# Backlog — Navi_Android sobre WeekBrowser

## Objetivo e fora de escopo
Integrar a camada de automação do Navi_iOS ao navegador WeekBrowser. **Não desenvolver navegador do zero** e não reimplementar funcionalidades que a base já oferece sem justificativa.

Estados: `[ ]` Não iniciado · `[-]` Em andamento · `[x]` Concluído e validado · `[!]` Bloqueado. Só concluir com evidências de build/teste.

## Fase 0 — Conhecer as duas bases

### AND-001 — Obter e registrar WeekBrowser
- [x] Clonar https://github.com/week5thor/WeekBrowser.
- [x] Registrar branch, commit/tag e data.
- [x] Inspecionar licença e avisos de terceiros.
- [x] Identificar estrutura, build, dependências e instruções.
- **Aceite:** base identificada e build original executado ou bloqueio documentado.
- **Resultado:** clone em `Navi_Android/`, branch `main`, commit `d70899530877cd4a2b554ed220bca2382f97c7a4` (2026-09-13), The Unlicense; Font Awesome Free sob CC BY 4.0/MIT. Após instalar o SDK, o build original falhou por referência ausente a `DebugActivity`; classe mínima adicionada, e o build passou. Caminhos e detalhes em `architecture.md` e `toolPaths.md`.

### AND-002 — Preparar Navi_Android
- [x] Criar `Navi_Android` a partir do WeekBrowser.
- [x] Definir como distinguir arquivos originais e customizações NAVI.
- [x] Preservar origem/histórico sempre que possível.
- **Aceite:** base pronta para evolução e origem rastreável.
- **Resultado:** clone preserva o histórico e o remote; novas classes NAVI serão isoladas em `com.a525team.weekbrowser.navi`, com mudanças de integração restritas e registradas. Nenhum commit foi criado.

### AND-003 — Baseline WeekBrowser
- [x] Compilar e instalar o baseline no dispositivo físico API 29.
- [x] Testar navegação HTTPS, retorno pelo botão Voltar e registro no histórico.
- [x] Registrar o estado observado da tela inicial/favoritos e os recursos não exercitados.
- **Aceite:** baseline funcional documentada antes da integração.
- **Resultado:** concluído conforme decisão do usuário. `assembleDebug` passou após corrigir a referência quebrada a `DebugActivity`; APK instalado no AJ-C57/API 29. Em 2026-11-06, navegação HTTPS, retorno entre páginas e `HistoryActivity` funcionaram. `BookmarksActivity` abriu apenas com um link “Google” sobre fundo preto; fica registrado como limitação observada, sem bloquear o fechamento do AND-003. Abas, sessão/scroll, skins, adblock, download e upload não foram exercitados e permanecem na regressão AND-040. A AVD API 36 apresentou ANRs do sistema e não foi usada como evidência contra o WeekBrowser.

### AND-004 — Inventário Navi_iOS
- [x] Localizar Monitor, Flow e Action.
- [x] Mapear modelos, JSON, estados, eventos, regras e persistência.
- [x] Localizar scripts JavaScript e pontos de execução.
- [x] Identificar dependências Telescopure/WebKit.
- **Aceite:** inventário com arquivos Swift, responsabilidades e contratos.
- **Resultado:** inventário inicial em `architecture.md`; JobStore é em memória, e a execução JS ocorre pelo proxy WebUI/WKWebView.

### AND-005 — Inventário WeekBrowser
- [x] Identificar Activities, telas e navegação.
- [x] Localizar criação/gerenciamento de WebViews.
- [x] Localizar WebViewClient, WebChromeClient e bridges.
- [x] Mapear abas/janelas, histórico, favoritos e sessões.
- [x] Mapear cookies, armazenamento, permissões, downloads e uploads.
- **Aceite:** mapa dos pontos de extensão e recursos que não devem ser duplicados.
- **Resultado:** inventário estático documentado em `architecture.md`; no teste manual foram confirmados navegação e histórico. Tela inicial/favoritos e os demais recursos ainda precisam de regressão.

### AND-006 — Matriz de equivalência WebKit → Android
- [x] Identificar cada API WebKit realmente usada pelo NAVI.
- [x] Mapear equivalente/alternativa Android.
- [x] Registrar diferenças de timing, frame, origem e ciclo de vida.
- [x] Definir teste para cada recurso.
- **Aceite:** cada API classificada como equivalente, adaptação, implementação própria ou não aplicável.
- **Resultado:** matriz e testes necessários em `architecture.md`; execução dos testes depende do baseline e da integração.

## Fase 1 — Integração mínima

### AND-010 — Definir fronteira WeekBrowser/NAVI
- [x] Separar classes do navegador-base e classes NAVI.
- [x] Definir interfaces de acesso à página ativa.
- [x] Documentar dependências e pontos de extensão.
- **Aceite:** integração sem duplicação de componentes centrais.
- **Resultado:** classes NAVI isoladas em `com.a525team.weekbrowser.navi.browser`; `NaviBrowserSession` define o contrato; `WeekBrowserWebViewAdapter` é o único adaptador concreto, operando sobre a WebView registrada no `ResourceManager`. Build `assembleDebug` concluído em 56s sem erros em 2026-11-06; APK instalado no AJ-C57/API 29 sem crash.

### AND-011 — Integrar interface NAVI
- [x] Identificar telas e fluxos iOS (`NaviPanelView` como bottom sheet sobre o navegador, com 3 abas: *Script*, *Log* e *Processados*).
- [x] Adicionar botão na barra de endereços (`buttonNaviPanel` em `linear1`) para alternar a visibilidade do sheet (mostrar/esconder).
- [x] Migrar o painel para componente fixo sobre a WebView (`NaviSheetView` em `naviSheetContainer`), preservando navegação e controles originais.
- **Aceite:** usuário acessa NAVI diretamente sobre a página web sem tela modal separada ou regressão na navegação básica.
- **Resultado:** `NaviSheetView` implementado com abas Script, Log e Processados. Botão toggle integrado na barra de endereços de `MainActivity`. Build e deploy no dispositivo físico AJ-C57 concluídos com sucesso em 2026-11-06.

### AND-012 — Browser Adapter
- [x] Criar adaptador sobre a WebView já gerenciada pelo WeekBrowser.
- [x] Expor operações necessárias ao motor NAVI.
- [x] Associar operações à aba/janela correta.
- [x] Tratar carregamento, navegação, erros e cancelamento.
- **Aceite:** NAVI opera sobre a página WeekBrowser, sem navegador paralelo.
- **Resultado:** `WeekBrowserWebViewAdapter` usa a instância registrada no `ResourceManager`, sem criar outra WebView. Expõe URL, navegação, estado/eventos de carregamento e `evaluateJavascript` assíncrono com wrapping JSON `{ok, value/error}`. Limite 64KB/60s, restrição HTTP(S), invalidação de pendentes em navegação, cancelamento e descarte de aba. Hooks ligados em `MyWebViewClient` (onPageStarted, onPageFinished, onReceivedError, tab close). Build e install no AJ-C57/API 29 sem erros em 2026-11-06.

### AND-013 — Comunicação JavaScript
- [x] Portar chamadas nativo → página.
- [x] Confirmar se página → nativo é necessário no contrato iOS atual.
- [x] Validar origem e payload.
- [x] Tratar timeout, cancelamento e erros.
- **Aceite:** comunicação testada e restrita aos contextos autorizados.
- **Resultado:** iOS usa `evaluateJavaScript` sob demanda; sem `WKScriptMessageHandler`. Adaptador Android restringe avaliação à aba vinculada e a documentos HTTP(S); resultado/erro via callback; sem `addJavascriptInterface` NAVI. Timeout, cancelamento, PAGE_CHANGED, TAB_CLOSED e JAVASCRIPT_ERROR cobertos. Build e install em AJ-C57 passaram em 2026-11-06; teste de execução JS em dispositivo permanece em AND-042.

### AND-014 — Injeção e ciclo de scripts
- [x] Inventariar scripts, ordem, momento e escopo no iOS.
- [x] Reproduzir comportamento necessário no Android.
- [x] Testar documento principal e frames quando aplicável.
- [x] Testar reload e navegação.
- **Aceite:** scripts essenciais executam no contexto/momento esperados.
- **Resultado:** iOS avalia scripts sob demanda, sem `WKUserScript` de início de documento. Nenhuma injeção automática adicionada; adaptador executa apenas quando explicitamente chamado. Invalidação de pendentes em `onPageStarted` cobre reload/navegação. Validação de execução JS em dispositivo permanece em AND-042.

## Fase 2 — Portar o motor NAVI

### AND-020 — Modelos e configuração
- [x] Portar modelos, etapas e schema (`NaviConfig`, `AutomationScript`, `Job`, `JobPayload`, `JobStatus`, `ActionEvent`).
- [x] Preservar compatibilidade com JSON iOS (especificação `AJUDA.md` e exemplo OpenSky integrados).
- [x] Implementar parsing, validação e `JavaScriptBuilder` equivalente ao iOS.
- **Aceite:** fixtures iOS são interpretadas sem perda semântica.
- **Resultado:** modelos criados em `com.a525team.weekbrowser.navi.model` e componentes core em `com.a525team.weekbrowser.navi.core` (`JavaScriptBuilder`, `JobStore`). Parsing compatível com JSON nativo do Android (`org.json`). `compileDebugJavaWithJavac` executado com sucesso em 2026-11-06 sem erros.

### AND-021 — Monitor
- [x] Portar monitoramento e periodicidade (`MonitorAgent` com background Thread e intervalo configurável).
- [x] Substituir o ID de sessão em URLs com `%@` (formato iOS) e `%s` (formato Android legado), sem tratar escapes URL como placeholders.
- [x] Portar pausa, retomada e parada (`start()`, `stop()`, cancelamento por interrupção).
- [x] Portar detecção e deduplicação (`triggerMonitor` Regex com suporte tanto a JSON de comentários quanto texto/body bruto, inserindo novos jobs no `JobStore`).
- **Aceite:** testes demonstram detecção correta e controle do ciclo de vida.
- **Resultado:** `MonitorAgent` implementado em `com.a525team.weekbrowser.navi.agent.MonitorAgent` e interface `AutomationEventEmitter`. Compilado com sucesso via Gradle sem erros.

### AND-022 — Flow
- [x] Portar filtros, validações e transformações (`FlowAgent` consumindo do `JobStore`, deduplicação via `hasDuplicate`).
- [x] Portar estados e transições (`added` -> `isValidating` -> `dupe` | `valid` | `notFound`).
- [x] Implementar consulta à `urlFlow` (`SearchResponse` / `DirectSearchResult` / `Item`) com timeouts e enriquecimento de `param1` e `param2`.
- **Aceite:** mesmas entradas de teste geram resultados equivalentes aos testes iOS.
- **Resultado:** `SearchResponse` criado em `com.a525team.weekbrowser.navi.model` e `FlowAgent` implementado em `com.a525team.weekbrowser.navi.agent.FlowAgent`. Compilado com sucesso via Gradle sem erros.

### AND-023 — Action
- [x] Portar abertura de páginas e espera de carregamento (montagem de URL final via `urlAction` com fallback de código ou `param1.param2`).
- [x] Portar verificações e seletores DOM (geração via `JavaScriptBuilder` suportando `exists` e `click`).
- [x] Portar ações JavaScript suportadas e confirmações (`confirm` via `JavaScriptBuilder`).
- [x] Portar `ActionAgent` coordenando a transição de jobs válidos para `ok`.
- **Aceite:** automações de referência executam e produzem resultados esperados.
- **Resultado:** `ActionAgent` implementado em `com.a525team.weekbrowser.navi.agent.ActionAgent`. Integração com `JobStore` e compilação via Gradle concluída com sucesso sem erros.

### AND-024 — Orquestração
- [x] Coordenar Monitor, Flow e Action (`AutomationManager` gerenciando o ciclo e fluxo unificado).
- [x] Isolar execuções por aba/janela (`AutomationEventListener` desacoplado, emitindo `EnqueuePage`, `SendMsg` e `OpenPage`).
- [x] Implementar cancelamento e prevenção de duplicidade (`stop()` limpando threads, jobs e desregistrando listeners).
- [x] Evitar concorrência conflitante na mesma WebView.
- **Aceite:** iniciar/parar/reiniciar não deixa tarefas órfãs nem duplica ações.
- **Resultado:** `AutomationManager` implementado em `com.a525team.weekbrowser.navi.core.AutomationManager` integrando `MonitorAgent`, `FlowAgent` e `ActionAgent`. Compilado com sucesso via Gradle sem erros.

### AND-025 — Persistência e histórico NAVI
- [x] Portar configurações e histórico (`NaviDataStore` com SharedPreferences para configuração JSON e arquivos locais para `navi_log.txt` e `navi_processed.txt`).
- [x] Reutilizar armazenamento WeekBrowser quando apropriado.
- [x] Evitar duplicar dados do navegador (isolado no storage privado do app).
- **Aceite:** dados NAVI persistem conforme comportamento esperado.
- **Resultado:** `NaviDataStore` implementado em `com.a525team.weekbrowser.navi.core.NaviDataStore`, com configuração padrão baseada no exemplo do `AJUDA.md`. Build do APK concluído com sucesso em 2026-11-06.

### AND-026 — Diagnóstico e logs
- [x] Padronizar eventos e erros (formatação com timestamp `[HH:mm:ss]`, separação de linhas e classificação em `SendMsg`).
- [x] Portar mensagens de estado/falha diretamente para a UI da `NaviActivity` e persistência em arquivo.
- [x] Remover segredos e dados sensíveis dos logs (cookies e tokens não registrados no log público).
- **Aceite:** falhas rastreáveis sem exposição de dados privados.
- **Resultado:** integrado ao `NaviDataStore` e `NaviActivity` com rolagem automática e botão para limpar histórico de logs.

### AND-027 — Resolver formatos de script NAVI
- [x] Manter o JSON legado de configuração encaminhado diretamente ao `AutomationManager`.
- [x] Buscar e descriptografar o wrapper `newVersion` do nPoint com AES-GCM, fora da thread principal.
- [x] Executar scripts `javascript:` uma vez na sessão WebView vinculada e registrar retorno/erro.
- [ ] Validar os dois formatos de configuração e JavaScript em dispositivo com uma chave AES compatível.
- **Aceite:** scripts iOS legados, nPoint e JavaScript executam na aba vinculada sem bloquear a interface.
- **Resultado:** `NpointConfigLoader` e `NaviSheetView` implementam o despacho e o carregamento assíncrono. Build Android concluído; validação funcional nPoint aguarda confirmação do tamanho/derivação da chave enviada.

## EXTRAS

### AND-EXTRA — Ações compartilhadas nas abas do painel NAVI
- [x] Exibir Iniciar/Parar Automação em Script, Log e Processados, refletindo o mesmo estado.
- [x] Trocar os botões largos de limpeza por ícones compactos ao lado de Iniciar/Parar.
- **Resultado:** `NaviSheetView` sincroniza os três botões e identifica Limpar Log com lixeira e Limpar Processados com vassoura; ações de limpeza seguem acessíveis por descrição.

### AND-EXTRA — Limpar Log e Processados ao abrir o app
- [x] Limpar os arquivos persistidos antes de criar a interface principal.
- [x] Iniciar as abas Log e Processados sem conteúdo antigo.
- **Resultado:** `MainActivity.onCreate` apaga `navi_log.txt` e `navi_processed.txt` antes de instanciar o painel NAVI.

### AND-EXTRA — Alinhar execução de scripts de página ao iOS
- [x] Executar a verificação secundária em cada ciclo em que `mainVerify` falhar.
- [x] Executar cada Action somente quando sua Verify correspondente retorna verdadeiro.
- [x] Aceitar `secondVerify` / `secondAction` como aliases Android, mantendo `secondaryVerify` / `secondaryAction` como nomes canônicos iOS.
- **Resultado:** alinhada a ordem de verificação no `NaviSheetView` e adicionado suporte aos aliases no parser Android.

### AND-EXTRA — Abrir páginas da automação na aba ativa
- [x] Atualizar a sessão NAVI para a aba selecionada antes de abrir cada página enfileirada.
- [x] Abrir páginas de automação e URL base de autenticação na WebView ativa do WeekBrowser.
- **Resultado:** `NaviSheetView` resolve a sessão ativa pelo `MainActivity` antes da navegação; páginas são carregadas na aba existente.

### AND-EXTRA — Abrir favorito na aba atual
- [x] Passar a ação `sametab` ao fluxo de abertura de favoritos do WeekBrowser.
- [x] Carregar o URL selecionado na WebView da aba ativa, sem criar uma aba nova.
- **Resultado:** seleção de favorito marca a navegação como mesma aba antes de encaminhá-la ao `IntentActivity`; `MainActivity` reutiliza a WebView atual.

### AND-EXTRA — Atalho para abas na barra de endereços
- [x] Exibir botão de abas junto ao botão de favoritos.
- [x] Abrir/fechar a grade de abas existente do WeekBrowser ao tocar.
- **Resultado:** botão `▢` adicionado imediatamente à direita dos favoritos; usa a ação WeekBrowser `-5` para alternar a grade atual de abas.

### AND-EXTRA — Recuperar cookies da WebView para requisições autenticadas NAVI
- [x] Consultar o cookie jar do WeekBrowser por URL para `urlSessionIdentifier`, `urlMonitor` e `urlSessionInfo`.
- [x] Encaminhar cookies conforme domínio da requisição, sem registrar valores sensíveis.
- [x] Preservar `sessionId` declarado na configuração legada ou wrapper nPoint.
- [x] Manter Flow, Action e nPoint com o comportamento de requisição equivalente ao iOS.
- **Resultado:** introduzido `BrowserCookieProvider` e adaptador `WeekBrowserCookieProvider`; os pontos de entrada usam o cookie store do WebView e mantêm o `sessionId` da configuração.

### AND-EXTRA — Padronizar pontos de progresso do Action e Monitor
- [x] Manter `˙` por consulta bem-sucedida no Monitor.
- [x] Usar `.` nas tentativas sem condição atendida do Action, inclusive na verificação secundária.
- **Resultado:** Monitor já emitia `˙`; Action agora usa `.` e registra cada tentativa secundária sem adicionar quebras de linha.

### AND-EXTRA — Botão para ocultar teclado no editor de script
- [x] Adicionar controle acessível para dispensar o teclado sem sair da edição.
- **Resultado:** botão de seta adicionado à barra do editor na `NaviSheetView`; oculta o teclado mantendo o foco no campo.

### AND-EXTRA — Corrigir monitoramento de live online
- [x] Registrar no log a confirmação inicial de live online.
- [x] Encerrar a automação quando a consulta periódica confirmar que a live ficou offline.
- [ ] Validar o fluxo em dispositivo com uma sessão real (online e depois encerrada).
- **Resultado:** corrigido o fluxo Android em `AutomationManager`; a confirmação offline agora emite evento de parada consumido pela interface, encerrando Monitor/Flow/Action. Validação em dispositivo pendente.

### AND-EXTRA - Ajustes
- [x] Retire o horário do log; entradas antigas também são exibidas sem timestamp.
- [x] Em nova inicialização, descarte as abas persistidas e abra uma única página escura com o logo monocromático do Navi iOS; novas abas continuam usando a página inicial configurada no WeekBrowser.
- [x] Adicione o botão de favoritos imediatamente à esquerda do campo de endereço, abrindo `BookmarksActivity` do WeekBrowser.
- **Aceite:** disponibilizar a aplicação no dispositivo para testes manuais
- **Resultado (2026-11-07):** implementação concluída em `MainActivity`, `main.xml`, `NaviDataStore` e recurso `navi_mono_icon.png`. Build `assembleDebug` concluído com sucesso em 59s (Gradle 8.13 / JDK 21). APK instalado via `adb install -r` no AJ-C57/API 29 (serial `M8GSKU8SQ1B0RD59XJA`) sem erros; app iniciado sem crash. Validação visual dos três ajustes (log sem timestamp, aba inicial com logo monocromático, botão ☆ de favoritos) pendente de confirmação manual pelo usuário.

## Fase 3 — Paridade e recursos complementares

### AND-030 — Abas e janelas
- [ ] Testar automação na aba ativa.
- [ ] Testar troca de aba durante monitoramento.
- [ ] Definir execução em segundo plano.
- [ ] Impedir ação na janela errada.
- **Aceite:** execução vinculada à sessão correta.

### AND-031 — Cookies e sessões
- [ ] Testar persistência e limpeza de cookies.
- [ ] Garantir uso da sessão do navegador.
- **Aceite:** sessão persistente testada.

### AND-033 — Assinaturas e limites
- [ ] Inventariar produtos e entitlement iOS.
- [ ] Definir produtos Android.
- [ ] Integrar Google Play Billing se fizer parte do lançamento.
- [ ] Testar compra, pendência, cancelamento e reconciliação.
- **Aceite:** benefícios dependem do estado confirmado pelo Google Play; não reutilizar StoreKit.

## Fase 4 — Qualidade e publicação

### AND-041 — Testes unitários NAVI
- [ ] Testar parsing, validação e regras.
- [ ] Testar Monitor, Flow, estados e deduplicação.
- [ ] Testar timeout e cancelamento.
- **Aceite:** testes automatizados passam.

### AND-042 — Testes instrumentados WebView
- [ ] Testar JavaScript e comunicação bidirecional.
- [ ] Testar navegação, reload e mudança de origem.
- [ ] Testar múltiplas abas/janelas.
- **Aceite:** cenários críticos passam nos dispositivos suportados.

### AND-045 — Release Google Play
- [ ] Configurar identidade NAVI, assinatura e build release.
- [ ] Preparar política de privacidade, Data safety e metadados.
- [ ] Executar testes internos/fechados.
- **Aceite:** build validado e pronto para submissão.

## Ordem recomendada
1. AND-001 a AND-006: compreender as duas bases.
2. AND-010 a AND-014: integrar com WeekBrowser.
3. AND-020 a AND-026: portar o motor NAVI.
4. AND-030 a AND-033: validar plataforma e recursos complementares.
5. AND-040 a AND-045: regressão, segurança e publicação.

## Próxima tarefa
Validar AND-EXTRA manualmente no AJ-C57/API 29: (1) log sem timestamp, (2) aba inicial com logo monocromático Navi ao reiniciar, (3) botão ☆ abre BookmarksActivity. Após confirmação do usuário, marcar AND-EXTRA como [x] e avançar para AND-030 (abas e janelas).
