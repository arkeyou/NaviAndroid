# Instruções para Agentes de Desenvolvimento — Navi_Android

## Missão
Construir o Navi_Android **a partir do WeekBrowser**, integrando a camada de automação existente no Navi_iOS. O iOS utiliza Telescopure + camada NAVI em Swift; o Android utilizará WeekBrowser + camada NAVI portada.

**Não desenvolver um navegador do zero nem substituir o WeekBrowser sem autorização explícita.**

## Bases e diretórios
- WeekBrowser: https://github.com/week5thor/WeekBrowser
- Referência funcional: `Navi_iOS`
- Destino: `Navi_Android`

Trabalhar em `Navi_Android`. Não alterar `Navi_iOS`, salvo solicitação explícita. Registrar branch, commit/tag e origem do WeekBrowser utilizado.

## Regra central: preservar WeekBrowser
Antes de implementar qualquer recurso, verificar se já existe na base. Preservar, quando presentes e funcionais: navegação, histórico, favoritos, tela inicial, pastas, widgets HTML, múltiplas janelas/links, sessões, posições de rolagem, skins, bloqueio de anúncios e anti-flood.

Não duplicar esses recursos na camada NAVI. Se for necessário alterar o navegador-base, justificar, limitar o escopo e adicionar teste de regressão.

## Processo por tarefa
1. Identificar a tarefa e critérios de aceite no `backlog.md`.
2. Localizar o comportamento equivalente no código iOS.
3. Localizar no WeekBrowser os componentes e pontos de extensão.
4. Verificar se a funcionalidade já existe.
5. Mapear as APIs WebKit utilizadas para Android WebView.
6. Implementar a menor alteração possível em `Navi_Android`.
7. Testar o recurso NAVI e as funcionalidades WeekBrowser afetadas.
8. Atualizar backlog e documentação.
9. Relatar arquivos, testes, resultados e limitações.

## Portabilidade da camada NAVI
Separar:
- Lógica independente de plataforma: modelos, parsing, JSON, Regex, regras, estados, deduplicação e Monitor/Flow/Action.
- Integração com navegador: carregamento, eventos, JavaScript, DOM, mensagens e navegação.
- Interface/ciclo de vida: telas, controles, alertas, notificações e permissões.

Portar primeiro a lógica independente de plataforma e depois adaptar a integração. Não fazer tradução mecânica Swift → Kotlin. O README do WeekBrowser informa Java/Sketchware Pro; inspecionar build e estrutura antes de escolher Java ou Kotlin. Evitar migração ampla da base.

## Android WebView e JavaScript
- Reutilizar a WebView gerenciada pelo WeekBrowser; evitar navegador ou WebView paralelo.
- Usar `evaluateJavascript()` quando apropriado e tratar retorno assíncrono.
- Investigar os momentos de injeção exigidos pelo iOS; `onPageFinished` não é substituto universal para `WKUserScript`.
- Considerar frames, navegação, reload e múltiplas janelas.
- Associar cada execução à aba/janela correta.
- Respeitar a thread principal e tratar timeout/cancelamento.
- Invalidar referências DOM após navegação.
- Restringir comunicação página → nativo por origem e validar payload.
- Não expor métodos privilegiados indiscriminadamente via `addJavascriptInterface`.
- Não ignorar erros TLS nem habilitar depuração/acesso a arquivos/conteúdo misto em produção sem justificativa.

## Modelo funcional
- **Monitor:** identifica eventos, periodicidade, pausa/parada e deduplicação.
- **Flow:** valida, filtra, transforma e coordena estados.
- **Action:** aguarda condições, localiza elementos, executa ações permitidas e solicita confirmação quando necessário.

O motor não deve depender diretamente de Activity ou de classes concretas do WeekBrowser. Usar interfaces/adaptadores.

## Segurança e qualidade
- Não enviar cookies, senhas ou tokens a serviços externos.
- Não registrar dados sensíveis.
- Validar URLs, origens e mensagens.
- Não contornar autenticação, CAPTCHA, paywall ou controles de acesso.
- Exigir confirmação para ações sensíveis/irreversíveis.
- Preservar proteções existentes no WeekBrowser.
- Revisar licenças e avisos de terceiros.
- Evitar refatorações amplas e atualizações de dependências sem necessidade.
- Criar testes unitários para lógica NAVI e instrumentados para WebView.

## Critérios de conclusão
Só marcar tarefa como concluída quando comportamento iOS e ponto de extensão WeekBrowser foram identificados, implementação está em `Navi_Android`, build e testes aplicáveis passaram, regressões do navegador-base foram verificadas e diferenças foram documentadas. Sem teste executado, marcar parcial ou bloqueada.

## Relatório do agente
Informar ID da tarefa; arquivos iOS e WeekBrowser analisados; arquivos Android alterados; recursos-base preservados/afetados; build/testes e resultados; limitações; documentação atualizada; próxima tarefa.
