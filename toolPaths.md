# Caminhos das ferramentas Android

Atualizado em 2026-11-07. Este arquivo registra caminhos verificados e funcionais; não instala, altera PATH global nem configura ferramentas.

| Ferramenta | Caminho/estado |
|---|---|
| OpenJDK 21 (funcional) | `JAVA_HOME=/usr/local/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home` |
| Executável Java | `/usr/local/opt/openjdk@21/bin/java` (versão 21.0.5) |
| Gradle 8.13 (cache do usuário) | `/Users/ericogomes/.gradle/wrapper/dists/gradle-8.13-bin/5xuhj0ry160q40clulazy9h7d/gradle-8.13/bin/gradle` |
| Android SDK | `ANDROID_HOME=$HOME/Library/Android/sdk` |
| Android SDK Command-line Tools | `$HOME/Library/Android/sdk/cmdline-tools/latest/bin/sdkmanager` (revision 21.0) |
| Android SDK Platform | `$HOME/Library/Android/sdk/platforms/android-34` |
| Android Build Tools | `$HOME/Library/Android/sdk/build-tools/34.0.0` e `35.0.0` |
| Android Platform Tools | `$HOME/Library/Android/sdk/platform-tools/adb` (37.0.1) |
| Gradle Wrapper do projeto | `Navi_Android/gradlew` não existe; usar o Gradle 8.13 do cache diretamente. |
| Android Studio | Não instalado neste Mac. |
| Dispositivo Android | `M8GSKU8SQ1B0RD59XJA` (modelo AJ-C57, API 29) — conectado via USB. |

## Deploy no dispositivo conectado (fluxo validado em 2026-11-07)

O projeto fica em `Navi_Android/`; o APK debug é gerado em `app/build/outputs/apk/debug/app-debug.apk`.

### 1. Verificar dispositivo conectado

```sh
"$HOME/Library/Android/sdk/platform-tools/adb" devices
```

Saída esperada:
```
List of devices attached
M8GSKU8SQ1B0RD59XJA    device
```

### 2. Compilar

Execute a partir da raiz do workspace (`Navi/`). As variáveis de ambiente abaixo são necessárias; passar como prefixo inline garante que o subprocesso as enxerga independentemente do shell atual:

```sh
JAVA_HOME=/usr/local/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home \
ANDROID_HOME=$HOME/Library/Android/sdk \
ANDROID_SDK_ROOT=$HOME/Library/Android/sdk \
"/Users/ericogomes/.gradle/wrapper/dists/gradle-8.13-bin/5xuhj0ry160q40clulazy9h7d/gradle-8.13/bin/gradle" \
  --project-dir "Navi_Android" \
  assembleDebug \
  --no-daemon
```

Resultado esperado:
```
BUILD SUCCESSFUL in ~59s
31 actionable tasks: N executed, N up-to-date
```

> **`--no-daemon`** é obrigatório neste ambiente. O daemon Gradle não consegue inicializar por restrições de socket local; sem essa flag o build trava.

### 3. Instalar no dispositivo

```sh
"$HOME/Library/Android/sdk/platform-tools/adb" -s M8GSKU8SQ1B0RD59XJA install -r \
  "Navi_Android/app/build/outputs/apk/debug/app-debug.apk"
```

Resultado esperado:
```
Performing Streamed Install
Success
```

### 4. Iniciar o app

```sh
"$HOME/Library/Android/sdk/platform-tools/adb" -s M8GSKU8SQ1B0RD59XJA shell monkey \
  -p com.a525team.weekbrowser 1
```

### 5. Verificar crashes (opcional)

```sh
"$HOME/Library/Android/sdk/platform-tools/adb" -s M8GSKU8SQ1B0RD59XJA logcat -d \
  -s AndroidRuntime:E
```

Saída limpa (apenas cabeçalhos `beginning of`) indica que o app iniciou sem crash.

---

## Notas

- Se o APK já foi gerado e apenas os sources não mudaram, omita o passo 2 e execute somente os passos 3–4.
- Para conferir qual dispositivo está conectado antes de instalar, use `adb devices` e ajuste o serial após `-s` se necessário.
- O emulador NaviApi36 (API 36) apresentou ANRs do sistema em testes anteriores e não deve ser usado como evidência de comportamento do app.
