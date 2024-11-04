# Praceando - API PostgreSQL

## Descrição

A **Praceando API** é uma API REST desenvolvida com **Spring Boot**, projetada para fornecer acesso e manipulação de dados no banco de dados **PostgreSQL**. Esta API serve como backend para o aplicativo **Praceando**, facilitando o gerenciamento de eventos públicos e incentivando a participação em atividades que promovam a sustentabilidade.

## Objetivo da API

A **Praceando API** foi desenvolvida para suportar o backend do aplicativo **Praceando**, garantindo a eficiência no gerenciamento de dados relacionados a eventos em espaços públicos. A API visa facilitar o acesso e a interação com esses dados, promovendo o objetivo do aplicativo de incentivar a sustentabilidade e a ocupação consciente de espaços urbanos.

## Tecnologias Utilizadas

- **Spring Boot** (Framework): Framework principal para o desenvolvimento da API.
- **PostgreSQL** (Banco de Dados): Banco de dados relacional utilizado para persistência de dados.
- **Redis** (Cache): Utilizado para caching, ajudando a melhorar a performance e reduzir o número de consultas ao banco de dados.
- **Swagger/OpenAPI (Springdoc)** (Documentação): Ferramenta para documentação interativa da API.
- **Spring Security** (Segurança): Framework para autenticação e autorização, protegendo os endpoints da API.
- **JWT (JSON Web Tokens)** (Autenticação): Implementado usando JJWT para manipulação e validação de tokens de autenticação.
- **Lombok** (Redução de Código): Biblioteca que reduz código repetitivo com anotações como `@Getter` e `@Setter`.
- **Hibernate** (ORM): Mapeamento objeto-relacional para facilitar a manipulação de dados no banco de dados.
- **Java Dotenv** (Gerenciamento de Variáveis de Ambiente): Gerencia variáveis de ambiente necessárias para configuração e segurança.
- **Hibernate Validator** (Validação de Dados): Valida dados com base em anotações nas entidades.
- **JUnit** e **Spring Security Test** (Testes): Frameworks para testes unitários e de integração, garantindo a qualidade do código.
- **Maven** (Build e Gerenciamento de Dependências): Gerenciador de build e dependências, essencial para manter o projeto organizado e com versões consistentes das bibliotecas.

## Como Executar a API

### Pré-requisitos

- JDK 11+
- Maven 3.6+
- PostgreSQL 13+

### Acesse a documentação da API em:

```
https://praceando-api-pg.onrender.com/swagger-ui/index.html
```

## Endpoints Principais

- **/api/evento**: Gerenciamento de eventos.
- **/api/usuario**: Gerenciamento de usuários.
- **/api/anunciante**: Gerenciamento de anunciantes.
- **/api/compra**: Gerenciamento de transações de compra.
- **/api/consumidor**: Gerenciamento dos dados dos consumidores.
- **/api/fraseSustentavel**: Gerenciamento de frases de sustentabilidade.
- **/api/genero**: Gerenciamento de gênero dos usuários.
- **/api/interesse**: Gerenciamento de interesses dos usuários em eventos e tags.
- **/api/local**: Gerenciamento de locais de eventos.
- **/api/pagamento**: Gerenciamento de transações de pagamento e finalização de compras.
- **/api/produto**: Gerenciamento de produtos.
- **/api/tag**: Gerenciamento de tags para eventos e interesses dos usuários.
- **/api/acesso**: Controle de acesso e autenticação.

---

Desenvolvido com ❤️ pela equipe de desenvolvimento Praceando:
- [Camilla Ucci](https://github.com/millaUcci)
- [Mayla Renze](https://github.com/mayren-07)

