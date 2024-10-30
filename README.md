# Praceando - API PostgreSQL

## Descrição

A **Praceando API** é uma API REST desenvolvida com **Spring Boot**, projetada para fornecer acesso e manipulação de dados no banco de dados **PostgreSQL**. Esta API serve como backend para o aplicativo **Praceando**, facilitando o gerenciamento de eventos públicos e incentivando a participação em atividades que promovam a sustentabilidade.

## Funcionalidades da API

1. **Gerenciamento de Eventos**:
   - CRUD (Create, Read, Update, Delete) para eventos.
   - Detalhes dos eventos, incluindo local, categoria, data, hora e descrição.

2. **Gerenciamento de Usuários**:
   - CRUD para usuários.
   - Controle de permissões para divulgação de eventos por organizadores.

3. **Gerenciamento de Compras e Produtos**:
   - CRUD para transações de compra e produtos.

4. **Interesses e Tags**:
   - Associação de tags aos interesses dos usuários e eventos.

5. **Gerenciamento de Anunciantes e Localidades**:
   - Controle de dados sobre anunciantes e locais onde ocorrem eventos.

6. **Frases Sustentáveis e Gênero**:
   - Funções auxiliares para frases de incentivo à sustentabilidade e categorização por gênero.


## Objetivo da API

A **Praceando API** foi desenvolvida para suportar o backend do aplicativo **Praceando**, garantindo a eficiência no gerenciamento de dados relacionados a eventos em espaços públicos. A API visa facilitar o acesso e a interação com esses dados, promovendo o objetivo do aplicativo de incentivar a sustentabilidade e a ocupação consciente de espaços urbanos.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para o desenvolvimento da API.
- **PostgreSQL**: Banco de dados relacional utilizado.
- **Swagger**: Ferramenta para documentação interativa da API.

## Como Executar a API

### Pré-requisitos

- JDK 11+
- Maven 3.6+
- PostgreSQL 13+

### Acesse a documentação da API em:

```
http://localhost:8080/swagger-ui.html
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

