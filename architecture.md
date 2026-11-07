# Navi_Android — arquitetura identificada

## Estado desta documentação

Inventário e primeiro teste manual feitos em 2026-11-06. O WeekBrowser foi obtido e sua origem está registrada abaixo. O baseline compilou e foi instalado no AJ-C57/API 29. Após o usuário informar que o dispositivo estava desbloqueado, foram verificados navegação HTTPS, retorno entre páginas e histórico. `BookmarksActivity` abriu apenas com um link “Google” sobre fundo preto; o resultado fica registrado como limitação observada. O usuário considerou o AND-003 concluído com esse escopo. As alterações da Fase 1 descritas abaixo ainda não foram compiladas nem testadas em dispositivo, a pedido do usuário.

## Bases e proveniência

| Item | Identificação |
|---|---|
| Navegador Android | WeekBrowser, `https://github.com/week5thor/WeekBrowser.git` |
| Diretório | `Navi_Android/` |
| Branch obtida | `main` |
| Commit obtido | `d70899530877cd4a2b554ed220bca2382f97c7a4` (`add src 8.1`) |
| Data do commit | 2026-09-13 |
| Tag | Nenhuma tag associada ao checkout; `git describe` aponta `d708995` |
| Linguagem/build | Java; Android Gradle Plugin 8.12.0; projeto originalmente desenvolvido com Sketchware Pro |
| Android | applicationId `com.a525team.weekbrowser`; compileSdk 34; minSdk 21; targetSdk 35; versão 8.1 (`versionCode` 3) |
| Licença indicada | The Unlicense (`Navi_Android/UNLICENSE`) |
| Terceiros identificados | Font Awesome Free: ícones sob CC BY 4.0 e MIT, conforme `Navi_Android/README.md` |

O clone preserva o histórico e o remote `origin`. Não foram criados commits. O checkout não contém Gradle Wrapper, mas há OpenJDK 21 e Gradle 8.13 no ambiente. Instalados Android SDK Command-line Tools, Platform 34, Build Tools 34.0.0/35.0.0 e Platform Tools no caminho padrão. O build inicial descobriu referência de compilação a `DebugActivity`, ausente embora declarada no Manifest; foi criada implementação mínima para exibir a exceção, restaurando o build sem alterar o fluxo normal do navegador. `assembleDebug` passou e o APK foi instalado no AJ-C57/API 29. O emulador API 36 apresentou ANRs do sistema e não foi usado para validar o navegador; a imagem de sistema API 29 está disponível, mas não há AVD API 29 configurado. Caminhos em `toolPaths.md`.

## WeekBrowser

O app é um módulo Android Java (`Navi_Android/app`) que usa o System WebView. `MainActivity` cria WebViews por aba e registra as instâncias e seus contêineres em `ResourceManager`, indexados por tag. A tela principal também coordena janelas/abas; `BookmarksActivity` contém a tela inicial baseada em HTML. A arquitetura observada não é uma aplicação Android convencional separada em módulos: há Activities e classes Java oriundas do Sketchware, layouts/recursos gerados e estado distribuído entre Activities, `SharedPreferences` e auxiliares.

Pontos de extensão observados:

- `MainActivity.setupWebView(...)` configura a WebView, instala os clients e associa bridges existentes (`Vidro`, `Android`, `AndroidColorPicker`). As bridges atuais servem a recursos do navegador e não devem ser reutilizadas como canal privilegiado do NAVI.
- `MyWebViewClient` trata navegação, interceptação de recursos, bloqueio de anúncios, extensões e erros SSL.
- `CustomWebClient` trata diálogos JavaScript, progresso, janelas, tela cheia/permissões e seleção de arquivos.
- `ExtensionUtil` injeta scripts de extensão em `onPageStarted`, `onPageFinished` e progresso 100%, e intercepta recursos. Esses callbacks não equivalem a uma garantia de execução antes dos scripts da página.
- `ResourceManager` mantém o vínculo entre identificador da aba e WebView/contêiner; a instância precisa ser resolvida no momento da ação, sem manter referências após descarte/navegação.

O README do WeekBrowser descreve histórico; favoritos, pastas e widgets HTML; modo de janelas e multilinks com sessão; posições de rolagem; skins; bloqueio de anúncios/anti-flood. O código confirma a estrutura de janelas/WebViews por aba, Activities de histórico e favoritos, downloads/uploads e o uso de `AdBlocker`. No teste manual, a navegação HTTPS, o retorno e a lista em `HistoryActivity` funcionaram; `BookmarksActivity` abriu com conteúdo aparentemente incompleto (link “Google” isolado em tela preta). Abas, sessão/scroll, skins, adblock, download e upload ainda aguardam regressão. Não substituir nem duplicar essas capacidades na camada NAVI.

O `AndroidManifest.xml` atual declara permissões de rede, localização, armazenamento, câmera, microfone, overlay e outras; também habilita cleartext traffic e backup. `MainActivity` habilita JavaScript, DOM storage e múltiplas janelas. Isso é inventário da base, não recomendação de endurecimento imediato: mudanças nessas configurações exigem análise de impacto específica e não devem vir junto da primeira integração NAVI.

### Ajuste de baseline

`SketcApplication` e o Manifest referenciam `.DebugActivity`, porém a classe não existia no commit obtido; o compilador falhava. Foi adicionada uma Activity pequena que exibe o stack trace enviado pelo handler global e permite fechá-la. A correção não altera navegação, permissões ou comportamento normal e é necessária para produzir/instalar o baseline. O APK foi reconstruído com sucesso.

## Referência iOS

O código NAVI está no Swift Package `Navi_iOS/LocalPackage`, target `Automation`. `AutomationManager` decodifica `NaviConfig`, gera scripts e coordena três agentes `@MainActor`:

- **MonitorAgent** consulta `urlMonitor`, aplica `triggerMonitor` (Regex), converte resultados em `JobPayload` e insere jobs no `JobStore`.
- **FlowAgent** consome jobs `.added`, deduplica pelo código e, quando configurado, consulta `urlFlow`; atualiza os estados e parâmetros do job.
- **ActionAgent** consome jobs `.valid`, monta a URL de ação e publica o job como `.ok`.
- **JobStore** é um actor com armazenamento em memória e streams de eventos; não é persistência de histórico.
- **JavaScriptBuilder** suporta atualmente `confirm`, `exists` e `click`. `ActionEvent` comunica mensagens, abertura de páginas e enfileiramento para a UI.
- `NaviPanelView` obtém cookies do `WKWebsiteDataStore.default().httpCookieStore` e consome eventos; `Browser` executa scripts pelo `WebViewProxyClient.evaluateJavaScript`. A conexão genérica `BrowserEngine` existe, mas não representa por si só uma integração NAVI implementada.

O navegador iOS tem UI em SwiftUI, camada de stores em `Model`, clientes de dependência em `DataSource` e `WKWebView` fornecido por WebUI. A configuração `.forNavi` não registra `WKUserScript` nem `WKScriptMessageHandler`; a execução NAVI identificada é por `evaluateJavaScript`, acionada pela aplicação, e não por injeção garantida no início de cada documento.

## Matriz inicial WebKit → Android

| Uso iOS identificado | Ponto Android candidato | Estado/diferença a preservar |
|---|---|---|
| WebView proxy: carregar URL/obter URL | WebView da aba selecionada em `ResourceManager`; `loadUrl`/`getUrl` | Adaptador NAVI ainda não existe. Resolver aba explicitamente e não criar outra WebView. |
| `evaluateJavaScript` assíncrono | `WebView.evaluateJavascript(script, ValueCallback)` | Executar na thread principal, consumir callback, reportar falhas/timeout e associar execução à aba/URL de origem correta. Não assumir execução em todos os frames. |
| Cookies do `WKHTTPCookieStore` | `CookieManager.getInstance().getCookie(url)` | API Android consulta cookies por URL, diferente de enumerar todos os cookies iOS. Restringir ao domínio necessário; não registrar nem enviar cookies fora dos endpoints já previstos. |
| `WKWebsiteDataStore` para dados do site | `CookieManager`, `WebStorage` e mecanismos de armazenamento do WebView | Não há equivalência 1:1; inventariar operações concretas antes de portá-las. |
| `WKNavigationDelegate` para navegação/falhas | `MyWebViewClient` | Reutilizar os callbacks existentes; validar redirecionamentos, mudança de origem e navegação durante execução. |
| `WKUIDelegate` para alert/confirm/prompt | `CustomWebClient` | Já há callbacks e UI para diálogos JavaScript. |
| `WKUserScript`/handler de mensagens | Nenhum uso NAVI iOS identificado | Não implementar por suposição. `ExtensionUtil` tem callbacks tardios, não garantia de document-start. Não expor API nativa ampla via `addJavascriptInterface`. |

Testes necessários ao implementar o adaptador: script na aba correta e retorno assíncrono; navegação/reload durante execução; mudança de origem; cancelamento/timeout; frames, se requisito iOS real os exigir; duas abas concorrentes; ausência de ação em WebView destruída. Os testes de regressão WeekBrowser devem cobrir navegação, histórico, favoritos/tela inicial, múltiplas janelas/multilinks, sessão/scroll, skins, adblock, download e upload.

## Fronteira de integração proposta

1. Manter as classes originais WeekBrowser no pacote atual e criar a lógica NAVI isolada sob `com.a525team.weekbrowser.navi` (modelos/configuração, parsing e estado não dependem de Activity).
2. Introduzir uma interface pequena de navegador e um adaptador Android que recebe a referência da WebView/aba já ativa; a camada Monitor/Flow/Action não recebe Activity nem `MainActivity`.
3. Alterar a UI e os pontos WeekBrowser somente para iniciar/parar NAVI e fornecer o adaptador correto. Registrar cada alteração feita em código original e cobri-la com regressão.
4. Manter scripts e payloads validados; evitar bridges página→nativo até haver requisito. Exigir confirmação nas ações sensíveis e preservar erros/cancelamentos explícitos.

Esta fronteira está sendo implementada sobre o baseline, sem port mecânico de Swift para Java/Kotlin. Não alterar identidade, permissões, política TLS, armazenamento ou recursos do WeekBrowser fora dos pontos de integração justificados.

## AND-EXTRA — Ajustes de uso

- `NaviDataStore` não acrescenta horário aos novos registros; `readLog()` remove o prefixo `[HH:mm:ss]` de registros antigos também.
- Em inicialização do `MainActivity`, a lista de abas persistida é descartada e substituída por uma aba `about:blank`. Ela carrega uma página HTML escura com o ícone `MonoIcon` copiado dos assets do iOS. A homepage configurada no WeekBrowser permanece em vigor para abas que o usuário abrir depois.
- O botão `☆` foi adicionado imediatamente à esquerda do campo de endereço e aciona o fluxo existente `BookmarksActivity` (`_webViewActions(-1, "")`); nenhum gerenciamento de favoritos foi duplicado.
- Implementação feita em 2026-11-07. Build `assembleDebug` concluído com sucesso em 59s (Gradle 8.13 / JDK 21, `--no-daemon`). APK instalado via `adb install -r` no AJ-C57/API 29 (serial `M8GSKU8SQ1B0RD59XJA`); app iniciado sem crash (logcat sem AndroidRuntime:E). Validação visual dos três ajustes pendente de confirmação manual pelo usuário.

## Fase 1 — integração mínima (em andamento)

### Fluxo iOS observado

`Browser` apresenta `NaviPanelView`, com áreas Script, Log e Processados. A tela permite importar/editar/salvar scripts e iniciar/parar a automação. A avaliação de JavaScript chega a `WebViewProxyClient.evaluateJavaScript`; a resposta é tratada no `Browser`. `ActionEvent` representa comandos de UI (`openPage`, mensagem e enfileiramento), não um canal de mensagens iniciado pelo documento. O runtime Monitor/Flow/Action continua fora do escopo desta etapa.

### Implementação Android parcial

- `com.a525team.weekbrowser.navi.browser.NaviBrowserSession` define o contrato independente da Activity para URL, navegação, estado/eventos de carregamento e avaliação assíncrona. Leitura de estado e controles de navegação são confinados à thread principal; resultados de JavaScript chegam por callback.
- `WeekBrowserWebViewAdapter` envolve a WebView registrada em `ResourceManager` para uma única tag de aba. Não cria WebView, não troca `WebViewClient`/`WebChromeClient` e não expõe métodos nativos ao JavaScript da página.
- `MyWebViewClient` notifica o adaptador sobre início/fim/falha principal de navegação e descarte da aba. O painel pode observar esses eventos; avaliações pendentes são invalidadas em navegação, timeout, cancelamento e encerramento.
- A avaliação só aceita script explícito, de tamanho limitado, na aba vinculada e em URL HTTP(S) sem credenciais. O retorno do `evaluateJavascript` é assíncrono e encaminha valor ou erro ao chamador na thread principal.
- O botão “NAVI Automação” no painel existente do WeekBrowser abre `NaviActivity` com o identificador da aba ativa. A tela inicial observa o carregamento e oferece uma ação controlada para ler `document.title`; não representa ainda o editor, importador, logs ou runtime iOS.
- Nenhum `addJavascriptInterface`, `WKScriptMessageHandler` ou bridge página→nativo foi criado. A inspeção iOS não mostrou necessidade para o fluxo atual; o callback do `evaluateJavascript` atende a resposta nativo→página.

Não foi adicionada injeção automática: a referência iOS executa os scripts sob demanda, sem `WKUserScript` de início de documento. O adaptador também não tenta automatizar frames. A tela NAVI e o adaptador ainda precisam de build e validação em aparelho; mudanças desta seção ficam provisórias até essa autorização/teste.
