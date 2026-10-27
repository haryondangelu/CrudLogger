Quem garante a instância única em cada versão?

No Singleton Clássico (AppLogger): A própria classe Java garante a instância única controlando seu ciclo de vida por meio de um construtor private e uma classe interna Holder (ou bloco sincronizado) que expõe uma única referência global estática.

Na versão Spring (SpringAppLogger): O Container IoC do Spring Framework garante a instância única ao registrar a classe anotada com @Component como um Bean no escopo padrão (Singleton Scope), gerenciando sua injeção de dependência na aplicação.

Quando o Singleton clássico ainda faz sentido?
O Singleton clássico faz sentido em aplicações Java puras (sem frameworks de inversão de controle como o Spring), em bibliotecas/SDKs reutilizáveis onde não se deve impor dependências externas de frameworks ao consumidor, ou para utilitários globais de baixo nível pré-inicialização do container.

Justificativa para a Escolha no Projeto Final
Decisão: Utilização do Singleton via Spring (@Component).

Justificativa: Em um ecossistema Spring Boot, o gerenciamento de dependências via Container IoC traz maior testabilidade (permitindo a substituição por Mocks em testes unitários), evita o acoplamento rígido de chamadas estáticas e permite configurar propriedades dinâmicas do logger via application.properties, integrando-se nativamente com o ciclo de vida do framework.
