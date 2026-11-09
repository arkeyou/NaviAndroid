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

- **MonitorAgent** consulta `urlMonitor`, aplica `triggerMonitor` (Regex), converte resultados em `JobPayload` e insere jobs no `JobStore`. O preenchimento do ID de sessão aceita `%@` do iOS e `%s` legado do Android, preservando escapes percentuais de URL como `%2F`.
- **FlowAgent** consome jobs `.added`, deduplica pelo código e, quando configurado, consulta `urlFlow`; atualiza os estados e parâmetros do job.
- **ActionAgent** consome jobs `.valid`, monta a URL de ação e publica o job como `.ok`.
- **JobStore** é um actor com armazenamento em memória e streams de eventos; não é persistência de histórico.
- **JavaScriptBuilder** suporta atualmente `confirm`, `exists` e `click`. `ActionEvent` comunica mensagens, abertura de páginas e enfileiramento para a UI.
- `NaviPanelView` obtém cookies do `WKWebsiteDataStore.default().httpCookieStore` e consome eventos; `Browser` executa scripts pelo `WebViewProxyClient.evaluateJavaScript`. A conexão genérica `BrowserEngine` existe, mas não representa por si só uma integração NAVI implementada.

O navegador iOS tem UI em SwiftUI, camada de stores em `Model`, clientes de dependência em `DataSource` e `WKWebView` fornecido por WebUI. A configuração `.forNavi` não registra `WKUserScript` nem `WKScriptMessageHandler`; a execução NAVI identificada é por `evaluateJavaScript`, acionada pela aplicação, e não por injeção garantida no início de cada documento.

## Assinaturas e billing (análise em 2026-11-09)

### Modelo atual no Navi_iOS

- `Navi_iOS/LocalPackage/Sources/DataSource/Repositories/IAPManager.swift` usa StoreKit 2 e define dois produtos: mensal `navipay01` e anual `navipay02`. O paywall busca os produtos da App Store, exibe os preços localizados, inicia compras, oferece restauração e consulta `Transaction.currentEntitlements` para reconhecer transações ativas e não revogadas. `Transaction.updates` atualiza o estado após compras e mudanças posteriores.
- O entitlement `isSubscribed` libera processamento sem limite. Para não assinantes, `NaviQueueConfig.dailyLimit` define 10 processamentos por dia. `NaviQueueTracker.recordEnqueue` é chamado após a ação principal (`NaviPanelView.swift`); apesar do nome, esse incremento conta o processamento nessa etapa, e não a inserção inicial na fila.
- `NaviQueueTracker` mantém data, contador diário e um cache booleano de assinatura em `UserDefaults`. O valor de assinatura é sincronizado pelas transações atuais do StoreKit ao iniciar/atualizar o `IAPManager`; o cache não é, por si só, a prova da compra.
- Há dois comportamentos de demonstração a não transportar para produção: o manager define preços fixos de fallback e, quando um plano não contém um `Product` carregado do StoreKit, `purchase(plan:)` simula uma compra e define `isSubscribed = true`. Em produção, indisponibilidade do catálogo deve impedir a compra, e os preços devem vir da loja.

### Equivalente Android

O modelo comercial é aplicável ao Android: planos mensal e anual, limite diário para não assinantes e entitlement ilimitado para assinantes. A implementação Android deve usar Google Play Billing; StoreKit e os identificadores da App Store não são reutilizáveis como transações. Os produtos precisam ser cadastrados no Play Console para o `applicationId` publicado, e preços e períodos devem vir do catálogo do Google Play.

Fluxo Android esperado:

1. Conectar `BillingClient` e carregar os detalhes dos produtos e ofertas da Play Store.
2. Iniciar a compra pelo fluxo do Google Play e tratar compra concluída, cancelada e pendente. Não conceder entitlement enquanto a compra estiver pendente.
3. Validar compras concluídas, conceder o entitlement somente após validação e reconhecer a compra inicial dentro do prazo exigido pelo Google. Renovação, cancelamento, expiração, revogação e mudanças de plano precisam atualizar o estado.
4. Consultar compras ao conectar e ao retomar o app, para recuperar compras feitas fora do fluxo atual e restaurar o acesso em outro dispositivo da mesma conta Play. Oferecer link para o usuário gerenciar/cancelar a assinatura na central do Google Play.
5. Aplicar no Android a mesma regra de uso diário; alinhar se a unidade contada continuará sendo o processamento bem-sucedido da ação principal ou se será alterada para outra etapa.

O projeto Android inspecionado ainda não tem dependência nem integração Billing (`Navi_Android/app/build.gradle` contém somente `fileTree`), e declara `applicationId` `com.a525team.weekbrowser`. O cadastro e a integração de produtos Play ainda não foram feitos. Como a assinatura libera funcionalidade digital dentro de um app distribuído pelo Google Play, a regra geral da loja exige Google Play Billing, salvo exceções de política aplicáveis.

### Validação e compartilhamento entre plataformas

O Google recomenda que o app envie o purchase token a um backend seguro para validar a compra com a Google Play Developer API, armazenar o entitlement e sincronizar eventos de ciclo de vida por Real-time Developer Notifications. A documentação admite reconhecimento no cliente para apps sem backend, mas a validação e o processamento no servidor oferecem maior resiliência contra fraude, falta de rede e mudanças de estado enquanto o app está fechado. Não persistir um `isSubscribed` local como única autoridade.

Sem login/identidade compartilhada e um serviço de entitlement comum, uma assinatura da App Store e uma do Google Play são independentes: cada uma libera o acesso no ecossistema em que foi comprada. Para permitir que uma única assinatura dê acesso em iOS e Android, será necessária uma conta compartilhada e backend que valide e una os entitlements das duas lojas. Esta decisão também afeta o cadastro dos produtos, a restauração e a interface de gerenciamento/cancelamento.

Referências oficiais consultadas:

- [Google Play Billing — integração e processamento de compras](https://developer.android.com/google/play/billing/integrate)
- [Google Play Billing — segurança e validação](https://developer.android.com/google/play/billing/security)
- [Google Play Billing — integração com backend e notificações de ciclo de vida](https://developer.android.com/google/play/billing/backend)
- [Google Play Billing — gerenciar assinaturas e compras](https://developer.android.com/google/play/billing/manage-purchases)
- [Política de pagamentos do Google Play](https://support.google.com/googleplay/android-developer/answer/9858738?hl=en)

### Implementação Android (2026-11-09, parcial)

- `NaviBillingManager` usa Google Play Billing Library 8.0.0; consulta os produtos de assinatura `navipay01` e `navipay02`, inicia a oferta retornada pela loja, sincroniza assinaturas ao abrir o app/retomar a Activity e reconhece compras concluídas. Compras pendentes não ativam acesso; compras concluídas não reconhecidas são encaminhadas a `acknowledgePurchase`.
- O diálogo `NaviPaywallDialog` abre pelo item Navi Premium adicionado ao diálogo de Configurações da automação (`NaviSettingsDialog`), acionado pelo ícone de ajustes no `NaviSheetView`. A organização segue `PaywallView.swift`: fechar no topo à direita; cabeçalho central com ícone e título; banner de limite/assinatura; planos anual e mensal em cartões com seleção; ação primária; restaurar; aviso legal. Preço e oferta são lidos de `ProductDetails`; sem catálogo, os cartões continuam visíveis com preço indisponível, sem inventar valores. Restaurar consulta novamente as assinaturas Play e gerenciar assinatura abre a central oficial de assinaturas do Google Play.
- `NaviDataStore` persiste contador diário e entitlement em armazenamento privado. O entitlement é atualizado pela lista de compras `PURCHASED` da Google Play; itens `PENDING` são ignorados. O fluxo Android aplica limite de 10 processamentos por data local e conta a conclusão da ação principal, acompanhando o ponto de contagem do iOS.
- Pendências: cadastrar os IDs e base plans/ofertas no Play Console; validar compra, pendência, cancelamento, expiração e restauração com licença de teste; conferência visual manual do usuário. `assembleDebug` passou em 2026-11-09 (Gradle 8.13; 31 tarefas) e APK foi instalado e iniciado no AJ-C57/API 29 para verificação manual.
- A checagem de compra no cliente não substitui validação e sincronização via backend. Sem backend/conta compartilhada, entitlement Android e iOS seguem independentes; `isSubscribed` local é cache derivado da resposta do Play e precisa ser reconciliado ao conectar/retomar.

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
- `NaviSheetView` reconhece três entradas sem substituir o parser existente: configuração JSON legada é enviada diretamente ao `AutomationManager`; wrapper `newVersion` faz GET HTTPS para `api.npoint.io/{id}/hash` em thread de trabalho e descriptografa o payload combinado nonce+ciphertext+tag com AES-GCM; prefixo `javascript:` executa uma vez na sessão WebView ativa pelo adaptador e aguarda callback assíncrono.
- O carregador nPoint não envia `secret` na requisição e não registra o conteúdo recebido. A chave é usada localmente como bytes UTF-8, conforme `SymmetricKey(data:)` no iOS, e precisa ter tamanho AES válido (16, 24 ou 32 bytes). Ainda falta validar a automação com um payload/chave compatíveis em aparelho.

Não foi adicionada injeção automática: a referência iOS executa os scripts sob demanda, sem `WKUserScript` de início de documento. O adaptador também não tenta automatizar frames. A tela NAVI e o adaptador ainda precisam de build e validação em aparelho; mudanças desta seção ficam provisórias até essa autorização/teste.
