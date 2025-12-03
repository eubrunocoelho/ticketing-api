# Ticketing API

Uma **API RESTful** robusta para um sistema de gestão de ticket, desenvolvida com **Java**, **Spring Boot**, **Spring Security** e **Autenticação JWT**. Oferece **controle de acesso baseado em função (RBAC)**, gestão completa de ciclo de vida dos tickets e documentação API de ponta.

# Sumário

- [Resumo](#resumo_da_api)
- [Rotas/API Endpoints](#rotas_api_endpoints)
- [Tecnologias](#tecnologias)
- [Configuração e Instalação](#configuracao_e_instalacao)

<span id="resumo_da_api"></span>

# Resumo da API

## Autenticação e Segurança

- Autenticação *JWT* Stateless
- Controle de Acesso Baseado em Função (*RBAC*)
- Criptografia de senhas (*BCrypt*) e proteção de *endpoints* via *Spring Security*

## Gerenciamento de Usuários

- Registro e Autenticação Segura
- Gestão de Dados
- Controle Administrativo
    - Consulta Avançada
    - Funções Exclusivas
    - Restrições de Segurança

## Gerenciamento de Tickets

- Clico de Vida Completo do Ticket
- Criação Restrita
- Consultas Otimizadas
- Controle de Modificação
- Gerenciamento de Status

## Respostas Integradas

- Comunicação *Staff-User*
- Restrições de Respostas
- Integridade dos Dados

## Gerenciamento de Categorias

- Acesso Público
- Gestão Exclusiva

## Documentação da API

- Interface Interativa do *Swagger*
- *OpenAPI 3.0*
- Documentação completa de *endpoints*

```
# Swagger URLs
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

<span id="rotas_api_endpoints"></span>

# Rotas/API Endpoints

## Gerenciamento de Usuários (`/users`)

### GET `/users`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/users`          |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                                                                                 |
|:----------|:----------|:--------------|:--------------|:------------------------------------------------------------------------------------------|
| `search`  | `string`  | Query         | Não           | Filtragem de pesquisa pelo valor parcial ou completo de `username` ou `email` do usuário. |
| `role`    | `string`  | Query         | Não           | Filtragem de dados pela função do usuário. (`ROLE_USER`, `ROLE_STAFF`, `ROLE_ADMIN`).     |
| `status`  | `string`  | Query         | Não           | Filtragem de dados pelo status do usuário. (`ACTIVE`, `INACTIVE`).                        |
| `sort`    | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`).                                                |
| `page`    | `integer` | Query         | Não           | Número referente à página.                                                                |

### POST `/users`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | POST              |
| Path          | `/users`          |

#### Restrições

- Campos Obrigatórios: `username`, `email`, `password`.
- Regras de Negócio:
    - Valores únicos para `username` e `email`.

### GET `users/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ter o `ID` igual ao `{id}`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do usuário a ser recuperado. |

### DELETE `users/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | DELETE            |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_ADMIN`.
- Regras de Negócio:
    - O usuário a ser deletado não pode ter a função de `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do usuário a ser deletado.   |

### PATCH `users/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | PATCH             |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ter o `ID` igual ao `{id}`.
- Campos Obrigatórios: `password`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do usuário a ser atualizado. |

### PATCH `users/{id}/status`

| Componente    | Valor                 |
|:--------------|:----------------------|
| Método HTTP   | PATCH                 |
| Path          | `/users/{id}/status`  |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_ADMIN`.
- Campos Obrigatórios: `status`.
- Regras de Negócio:
    - O usuário a ser atualizado não pode ter a função de `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do usuário a ser atualizado. |

### PATCH `users/{id}/role`

| Componente    | Valor                 |
|:--------------|:----------------------|
| Método HTTP   | PATCH                 |
| Path          | `/users/{id}/role`    |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_ADMIN`.
- Campos Obrigatórios: `role`.
- Regras de Negócio:
    - O usuário a ser atualizado não pode ter a função de `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do usuário a ser atualizado. |

## Gerenciamento de Tickets (`/tickets`)

### GET `/tickets`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/tickets`        |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição                                                                                     |
|:--------------|:----------|:--------------|:--------------|:----------------------------------------------------------------------------------------------|
| `search`      | `string`  | Query         | Não           | Filtragem de pesquisa pelo valor parcial de `title` do ticket.                                |
| `status`      | `string`  | Query         | Não           | Filtragem de dados pelo `status` do ticket. (`OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`).    |
| `category`    | `integer` | Query         | Não           | Filtragem de dados pelo `ID` de `category`.                                                   |
| `user`        | `string`  | Query         | Não           | Filtragem de dados pelo `username` ou `email` do usuário proprietário do ticket.              |
| `sort`        | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`, `LAST_UPDATE`, `NAME`).                             |
| `page`        | `integer` | Query         | Não           | Número referente à página.                                                                    |


### POST `/tickets`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | POST              |
| Path          | `/tickets`        |
| Autenticação  | JWT Bearer Token  |

#### Restrições

- Campos Obrigatórios: `email`, `username`, `password`.
- Regras de Negócio:
    - Usuários com função de `ROLE_STAFF` ou `ROLE_ADMIN` não podem criar tickets.

### GET `tickets/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket a ser recuperado.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do ticket a ser recuperado.  |

### DELETE `tickets/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | DELETE            |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket a ser deletado.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do ticket a ser deletado.    |

### PATCH `tickets/{id}`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | PATCH             |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propietário: O usuário autenticado deve ser o proprietário do ticket a ser atualizado.
- Campos Opcionais: `category`, `title`, `content`.
- Regras de Negócio:
    - Tickets com o `status` definido como `IN_PROGRESS`, `RESOLVED` ou `CLOSED` não podem ser atualizados.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do ticket a ser atualizado.  |

### PATCH `tickets/{id}/status`

| Componente    | Valor                     |
|:--------------|:--------------------------|
| Método HTTP   | PATCH                     |
| Path          | `/tickets/{id}/status`    |
| Autenticação  | JWT Bearer Token          |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Campos Obrigatórios: `status`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição                         |
|:----------|:----------|:--------------|:--------------|:----------------------------------|
| `id`      | `integer` | Path          | Sim           | `ID` do ticket a ser atualizado.  |

### GET `tickets/user/{userId}`

| Componente    | Valor                     |
|:--------------|:--------------------------|
| Método HTTP   | PATCH                     |
| Path          | `/tickets/user/{userId}`  |
| Autenticação  | JWT Bearer Token          |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ter o `ID` igual ao `{userId}`.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição                                                             |
|:--------------|:----------|:--------------|:--------------|:----------------------------------------------------------------------|
| `userId`      | `integer` | Path          | Sim           | `ID` do usuário.                                                      |
| `search`      | `string`  | Query         | Não           | Filtragem de pesquisa pelo valor parcial de `title` do ticket.        |
| `status`      | `string`  | Query         | Não           | Filtragem de dados pelo status do usuário. (`ACTIVE`, `INACTIVE`).    |
| `category`    | `integer` | Query         | Não           | Filtragem de dados pelo `ID` de `category`.                           |
| `sort`        | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`, `LAST_UPDATE`, `NAME`).     |
| `page`        | `integer` | Query         | Não           | Número referente à página.                                            |

## Gerenciamento de Respostas dos Tickets

### GET `tickets/{ticketId}/reply`

| Componente    | Valor                         |
|:--------------|:------------------------------|
| Método HTTP   | GET                           |
| Path          | `tickets/{ticketId}/reply`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket referente as respostas recuperadas.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição                                                                             |
|:--------------|:----------|:--------------|:--------------|:--------------------------------------------------------------------------------------|
| `ticketId`    | `integer` | Path          | Sim           | `ID` do ticket.                                                                       |
| `user`        | `string`  | Query         | Não           | Filtragem de dados pelo `username` ou `email` do usuário proprietário das resposta.   |
| `sort`        | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`, `LAST_UPDATE`).                             |
| `page`        | `integer` | Query         | Não           | Número referente à página.                                                            |

### POST `tickets/{ticketId}/reply`

| Componente    | Valor                         |
|:--------------|:------------------------------|
| Método HTTP   | POST                          |
| Path          | `/tickets/{ticketId}/reply`   |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket para cadastrar respostas.
- Campos Obrigatórios: `content`. 
- Regras de Negócio:
    - O usuário proprietário não pode responder diretamente seu próprio ticket.
    - O usuário proprietário não pode responder diretamente sua própria resposta.
    - Para respostas subsequentes o usuário autenticado deve ser o proprietário do ticket a ser respondido.
    - Não é possível responder um ticket com `status` definido como `RESOLVED` ou `CLOSED`.

#### Parâmetros da Rota

| Nome       | Tipo      | Localização   | Obrigatório   | Descrição                                            |
|:-----------|:----------|:--------------|:--------------|:-----------------------------------------------------|
| `ticketId` | `integer` | Path          | Sim           | `ID` do ticket em que sera cadastrada a resposta.    |

### GET `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                                 |
|:--------------|:--------------------------------------|
| Método HTTP   | GET                                   |
| Path          | `tickets/{ticketId}/reply/{replyId}`  |
| Autenticação  | JWT Bearer Token                      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket referente a resposta recuperada.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição         |
|:--------------|:----------|:--------------|:--------------|:------------------|
| `ticketId`    | `integer` | Path          | Sim           | `ID` do ticket.   |
| `replyId`     | `integer` | Path          | Sim           | `ID` da resposta. |

### DELETE `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                                 |
|:--------------|:--------------------------------------|
| Método HTTP   | DELETE                                |
| Path          | `tickets/{ticketId}/reply/{replyId}`  |
| Autenticação  | JWT Bearer Token                      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket referente a resposta a ser deletada.
- Regras de Negócio:
    - Uma resposta que já foi respondida não pode ser deletada.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição         |
|:--------------|:----------|:--------------|:--------------|:------------------|
| `ticketId`    | `integer` | Path          | Sim           | `ID` do ticket.   |
| `replyId`     | `integer` | Path          | Sim           | `ID` da resposta. |

### PATH `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                         |
|:--------------|:------------------------------|
| Método HTTP   | Path                          |
| Path          | `tickets/{ticketId}/reply`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Proprietário: O usuário autenticado deve ser o proprietário do ticket e da resposta a ser atualizada.
- Campos Obrigatórios: `content`.
- Regra de Negócio: Uma resposta já respondida não pode ser atualizada.

#### Parâmetros da Rota

| Nome          | Tipo      | Localização   | Obrigatório   | Descrição         |
|:--------------|:----------|:--------------|:--------------|:------------------|
| `ticketId`    | `integer` | Path          | Sim           | `ID` do ticket.   |
| `replyId`     | `integer` | Path          | Sim           | `ID` da resposta. |

## Gerenciamento de Categorias (`/categories`)

### GET `/categories`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/categories`     |
| Autenticação  | JWT Bearer Token  |

### POST `/categories`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | POST              |
| Path          | `/categories`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Campos Obrigatórios: `name`, `priority`.
- Campos Opcionais: `description`.
- Regras de Negócio:
    - Valores únicos para `name`.

### GET `/categories/{id}`

| Componente    | Valor                 |
|:--------------|:----------------------|
| Método HTTP   | GET                   |
| Path          | `/categories/{id}`    |
| Autenticação  | JWT Bearer Token      |

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição             |
|:----------|:----------|:--------------|:--------------|:----------------------|
| `id`      | `integer` | Path          | Sim           | `ID` da categoria.    |

### DELETE `/categories/{id}`

| Componente    | Valor                 |
|:--------------|:----------------------|
| Método HTTP   | DELETE                |
| Path          | `/categories/{id}`    |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição             |
|:----------|:----------|:--------------|:--------------|:----------------------|
| `id`      | `integer` | Path          | Sim           | `ID` da categoria.    |

### PATCH `/categories/{id}`

| Componente    | Valor                 |
|:--------------|:----------------------|
| Método HTTP   | PATCH                 |
| Path          | `/categories/{id}`    |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função de `ROLE_STAFF` ou `ROLE_ADMIN`.
- Campos Opcionais: `name`, `description`, `priority`.
- Regras de Negócio:
    - Valores únicos para `name`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição             |
|:----------|:----------|:--------------|:--------------|:----------------------|
| `id`      | `integer` | Path          | Sim           | `ID` da categoria.    |

## Autenticação de Usuários (`/auth`)

### POST `/auth`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/auth`           |

#### Restrições

- Campos Obrigatórios: `username`, `password`.
- Regras de Negócio:
    - O valor para `username` pode ser o `username` ou `email` do usuário.
    - As credenciais de usuário devem ser válidas.


### GET `/auth/user`

| Componente    | Valor             |
|:--------------|:------------------|
| Método HTTP   | GET               |
| Path          | `/auth/user`      |
| Autenticação  | JWT Bearer Token  |

<span id="tecnologias"></span>

# Tecnologias

Tecnologias utilizadas no projeto.

## Construção da API

- Java
- Spring Boot
- Spring Boot Starter Web
- Spring Security
- Spring Boot Validation
- BCrypt
- JJWT
- Lombok
- MapStruct

## Banco de Dados

- Spring Data JPA
- Hibernate
- MySQL

## Documentação

- Springdoc-OpenAPI UI

## IDE, Versionamento, Estilo de Código

- IntelliJ IDEA Community
- Insomnia
- Postman
- Git
- GitHub
- CheckStyle

<span id="configuracao_e_instalacao"></span>

# Configuração e Instalação

## Requisitos

- Apache Maven 3.9.11 *(Versão recomendada)*
- JDK 17.0.12 *(Versão recomendada)*
- MySQL > 8.0 *(Versão recomenda)*

## Instalação

```
# 1. Executar as consultas listadas em:
# ./src/main/resources/schemas.sql

# 2. Renomear arquivo .env.example para .env

# 3. Preencher as variáveis de ambiente presentes em .env

# 4. Executar os seguintes comandos:
mvn clean install
mvn spring-boot:run

# 5. Criar um usuário pela rota: [POST] /users
# 6. Executar a seguinte consulta para definir o usuário como administrador:
UPDATE users
SET role = 'ROLE_ADMIN'
WHERE username = '<username>';
```

---

Desenvolvido com 🤍 e ☕ por [@eubrunocoelho](https://github.com/eubrunocoelho).
