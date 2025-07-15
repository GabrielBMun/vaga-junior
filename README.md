# 🔹 Desafio Técnico Júnior #1 – Cadastro e Consulta de Abastecimentos - Posto Combustível - Sistema de Gerenciamento

Sistema simples para gerenciamento de combustíveis, bombas e abastecimentos em um posto de combustível, usando Java com JPA/Hibernate e PostgreSQL.

Como pedido no desafio da vaga, desenvolvi a aplicação o mais bem estruturada possível, cumprindo todos os requisitos e todos (senão quase) os opcionais.

## Funcionalidades

- Cadastro, edição, listagem e exclusão de **Combustíveis**
- Cadastro, edição, listagem e exclusão de **Bombas** associadas aos combustíveis
- Cadastro, edição, listagem e exclusão de **Abastecimentos** com controle de data, quantidade e valor total calculado automaticamente

## Tecnologias

- Java
- Jakarta Persistence API (JPA) com Hibernate
    - Utiliza `EntityManagerFactory` para gerenciar conexões com o banco
    - O mapeamento objeto-relacional lida com a maior parte das consultas automaticamente, sem necessidade de SQL manual
- PostgreSQL
- Swing para interface gráfica
- EntityManagerFramework

## Estrutura do projeto

- `model` - entidades JPA que representam as tabelas do banco  
- `dao` - classes para acesso direto ao banco  
- `service` - lógica de negócio e controle de transações  
- `view` - telas Swing para interação com o usuário  
- `util` - utilitários gerais, como o gerenciador de EntityManager
- `controller` - as controllers, não desenvolvidas mas posicionadas na estrutura do projeto

## Observações

- O banco é atualizado automaticamente com base nas entidades (`hibernate.hbm2ddl.auto=update`).
- Valores totais de abastecimento são calculados com base no preço do combustível associado à bomba.  
- Interface desktop simples e funcional, ideal para uso local.
- As controllers do projeto não desenvolvidas por optar pela visualização via Swing e não por uma API Restful ou similar, achei que seria mais simples uma tela swing básica para "dizer que funciona", os métodos poderiam ser chamados em um endpoint caso necessário, mas não foi uma solução implementada.
- As telas que abrem ao clicar no botão são modals, não JFrames, bloqueiam o uso da tela anterior até que sejam fechadas.
- Não progredi muito na estilização do Swing, foi minha primeira vez trabalhando com ele e tentei fazer o mais prático possível e visualmente autoexplicativo.
- Caso ocorra erros na hora de rodar, por favor me avise! Está funcionando corretamente para mim, talvez o POM dê erro de novo e precise alterar a classe main, mas me avise!
