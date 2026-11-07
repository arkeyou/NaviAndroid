# Navi_Android

## Visão geral
O NAVI — Navegação Inteligente combina navegação tradicional com automação web. A versão Android será construída a partir do projeto open source **WeekBrowser**, adicionando a camada de automação portada do Navi_iOS.

**Não desenvolver um navegador do zero.** A estratégia repete a adotada no iOS:

| Plataforma | Navegador-base | Camada NAVI |
|---|---|---|
| iOS | Telescopure | Automação em Swift |
| Android | WeekBrowser | Automação portada para Android |

Base: https://github.com/week5thor/WeekBrowser

O repositório informa que WeekBrowser é escrito em Java para Android, utiliza Android System WebView e foi desenvolvido com Sketchware Pro. A licença indicada é The Unlicense; verificar também avisos e licenças de componentes de terceiros na versão efetivamente utilizada.

## Funcionalidades NAVI
- **Automação web:** fluxos que acessam páginas, aguardam carregamentos, interagem com elementos e executam ações.
- **Scripts de automação:** JavaScript no contexto das páginas visitadas para verificações e interações.
- **Fluxos e tarefas:** sequências reutilizáveis para tarefas repetitivas.
- **Monitor:** identifica eventos e mudanças.
- **Flow:** processa, valida e transforma informações.
- **Action:** executa ações definidas.

## Estratégia de reaproveitamento
1. Obter e compilar o WeekBrowser sem alterações.
2. Inspecionar sua estrutura, dependências e pontos de extensão.
3. Inspecionar a camada NAVI em `Navi_iOS`.
4. Portar a lógica NAVI para Android, adaptando a integração ao WebView do WeekBrowser.
5. Preservar as funcionalidades originais do WeekBrowser.
6. Validar paridade com cenários reais da versão iOS.

## Recursos do WeekBrowser a preservar
O README do projeto descreve navegação com System WebView, histórico, tela inicial com favoritos e pastas, widgets HTML, múltiplas janelas/links, salvamento de sessões, posições de rolagem, skins globais e bloqueio de anúncios/anti-flood. Confirmar cada recurso no código e em testes. Não removê-los nem reimplementá-los na camada NAVI sem necessidade comprovada.

## Estrutura esperada
```text
/
├── Navi_iOS/       # Referência funcional Swift
├── Navi_Android/   # WeekBrowser adaptado com camada NAVI
├── README.md
├── agents.md
└── backlog.md
```

## Princípios
- Reutilizar antes de reimplementar.
- Manter as mudanças no WeekBrowser pequenas e justificadas.
- Separar a lógica Monitor/Flow/Action dos detalhes de Activity e WebView.
- Portar comportamento, não sintaxe Swift.
- Preservar os formatos de configuração e a semântica das automações.
- Testar funcionalidades NAVI e regressões no navegador-base.

## Critérios gerais de aceite
O projeto compila; os recursos WeekBrowser selecionados continuam funcionando; Monitor/Flow/Action executam cenários equivalentes aos do iOS; JavaScript roda no contexto esperado; estados, erros e cancelamentos são tratados; diferenças e limitações estão documentadas.

## Observação
O inventário técnico depende da inspeção dos códigos completos de `Navi_iOS` e WeekBrowser. Os READMEs não bastam para confirmar todos os pontos de extensão ou APIs efetivamente utilizadas.


## Especificação de Configuração da Automação (Schema & Modelo)
Fonte: `AJUDA.md` (NaviBrowser)

### Campos de Configuração (`NaviConfig`)

- **`urlMonitor`** (String): URL da página ou serviço consultado periodicamente para identificar novos eventos/dados.  
  *Exemplo:* `"https://opensky-network.org/api/states/all?lamin=-22.95&lomin=-51.30&lamax=-14.10&lomax=-39.80"`
- **`triggerMonitor`** (String): Padrão Regex utilizado para identificar informações no conteúdo monitorado.  
  *Exemplo:* `"[A-Z]{3}[0-9]{4}"`
- **`urlAction`** (String): URL base acessada quando o evento monitorado for detectado.  
  *Exemplo:* `"https://www.flightaware.com/live/flight/"`
- **`mainVerify`** (`AutomationScript`): Verificação realizada na página antes da ação.
- **`mainAction`** (`AutomationScript`): Ação executada pelo NAVI quando a condição for atendida.
- **`secondaryVerify`** / **`secondaryAction`** (`AutomationScript`, opcional): Verificação e ação secundárias opcionais.
- **`urlFlow`** (String, opcional): URL intermediária para validação e enriquecimento do job.
- **`urlSessionIdentifier`** / **`urlSessionInfo`** (String, opcional): Endpoints de gerenciamento de sessão/login quando aplicável.

### Modelo de Script de Automação (`AutomationScript`)
- **`type`**: `"exists"` | `"confirm"` | `"click"`
- **`selector`**: Seletor CSS do elemento (usado em `"exists"` e `"click"`). Ex: `"div[class='flightPageFriendlyIdentLbl']"`, `"button#comprar"`.
- **`customSelector`** (opcional): Expressão complementar JS avaliada em conjunto.
- **`message`**: Mensagem exibida no alerta de confirmação nativo/web (usado em `"confirm"`). Ex: `"Ir para o proximo?"`.

### Exemplo Prático de Configuração (JSON)
```json
{
  "urlMonitor": "https://opensky-network.org/api/states/all?lamin=-22.95&lomin=-51.30&lamax=-14.10&lomax=-39.80",
  "triggerMonitor": "[A-Z]{3}[0-9]{4}",
  "urlAction": "https://www.flightaware.com/live/flight/",
  "mainVerify": {
    "type": "exists",
    "selector": "div[class='flightPageFriendlyIdentLbl']"
  },
  "mainAction": {
    "type": "confirm",
    "message": "Ir para o proximo?"
  }
}
```
