# Ticketing API

Uma **API RESTful** robusta para um sistema de gestão de ticket, desenvolvida com **Java**, **Spring Boot**, **Spring Security** e **Autenticação JWT**. Oferece **controle de acesso baseado em função (RBAC)**, gestão completa de ciclo de vida dos tickets e documentação API de ponta.

## Autenticação e Segurança

- Autenticação Stateless (JWT).
- Controle de Acesso Baseado em Função (RBAC) com sistema de permissões hierárquico (ROLE_USER, ROLE_STAFF, ROLE_ADMIN).
- Criptografia de senhas usando BCrypt.
- Controle de expiração de token para mitigar riscos de segurança.
- Proteção e segurança de endpoints usando Spring Security.

## Gestão Inteligente de Usuários

- Registro e autenticação segura de usuários.
- Funcionalidade de consulta de usuários com recursos de ordenação e filtragem de dados.
- Usuários inativos são impedidos de acessar aos endpoints privados da API.
- Gestão abrangente de dados, segurança e senhas do usuário.

## Gerenciamento de Categorias

- Controle de segurança para todas as operações de categorias.

## Gestão de Ciclo de Vida do Ticket

- Controle de segurança para todas as operações de tickets.
- Tickets podem ser destinados a uma categoria ou setor responsável específico.
- Consultas otimizadas com recursos de ordenação e filtragem de dados.
- Ciclo de vida do ticket baseados em status (OPEN, IN_PROGRESS, RESOLVED, CLOSED).
- Atribuição e rastramento de usuários.
- Estrutura de dados robusta para mapear a relação entre usuários e tickets.

## Gerenciamento Integrado de Respostas

- Controle de segurança para todas as operações de respostas.
- Atribuição e rastreamento de usuários, tickets e respostas associadas.
- Consulta de respostas com recursos de ordenação e filtragem de dados.
- Mapeamento de relacionamento entre usuários, resposta e tickets.

## Rotas

## Rotas de Gerenciamento de Usuários (`/users`)

### GET `/users`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | GET               |
| Path          | `/users`          |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `search`  | `string`  | Query         | Não           | Filtragem de pesquisa pelo `username` ou `email` do usuário. |
| `role`    | `string`  | Query         | Não           | Filtragem pela função do usuário. (`ROLE_USER`, `ROLE_STAFF` ou `ROLE_ADMIN`) |
| `status`  | `string`  | Query         | Não           | Filtragem pelo status do usuário. (`ACTIVE`, `INACTIVE`) |
| `sort`    | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`) |
| `page`    | `integer` | Query         | Não           | Número da página. |

### POST `/users`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | POST              |
| Path          | `/users`          |

#### Restrições

- Campos obrigatórios: `email`, `username`, `password`.

### GET `users/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | GET               |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ter o `ID` igual ao `{id}`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do usuário a ser recuperado. |

### DELETE `users/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | DELETE            |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_ADMIN`.
- Regra de negócio: O usuário a ser deletado não pode ter a função `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do usuário a ser deletado. |

### PATCH `users/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | PATCH             |
| Path          | `/users/{id}`     |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ter o `ID` igual ao `{id}`.
- Campos obrigatórios: `password`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do usuário a ser atualizado. |

### PATCH `users/{id}/status`

| Componente    | Valor                 |
|---------------|-----------------------|
| Método HTTP   | PATCH                 |
| Path          | `/users/{id}/status`  |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_ADMIN`.
- Regra de negócio: O usuário a ser atualizado não pode ter a função `ROLE_ADMIN`.
- Campos obrigatórios: `status`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do usuário a ser atualizado. |

### PATCH `users/{id}/role`

| Componente    | Valor                 |
|---------------|-----------------------|
| Método HTTP   | PATCH                 |
| Path          | `/users/{id}/role`    |
| Autenticação  | JWT Bearer Token      |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_ADMIN`.
- Regra de negócio: O usuário a ser atualizado não pode ter a função `ROLE_ADMIN`.
- Campos obrigatórios: `role`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do usuário a ser atualizado. |

## Gerenciamento de Tickets (`/tickets`)

### GET `/tickets`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | GET               |
| Path          | `/tickets`        |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `search`  | `string`  | Query         | Não           | Filtragem de pesquisa pelo `title` do ticket. |
| `status`  | `string`  | Query         | Não           | Filtragem pelo status do ticket. (`OPEN`, `IN_PROGRESS`, `RESOLVED` ou `CLOSED`). |
| `category` | `integer`  | Query         | Não           | Filtragem pelo `ID` de category. |
| `user`    | `string`  | Query         | Não           | Filtragem pelo `username` ou `email` do usuário. |
| `sort`    | `string`  | Query         | Não           | Ordenação de resultados. (`NEW`, `OLDER`, `LAST_UPDATE` ou `NAME`). |
| `page`    | `integer` | Query         | Não           | Número da página. |


### POST `/tickets`

| Componente    | Valor                 |
|---------------|-----------------------|
| Método HTTP   | POST                  |
| Path          | `/tickets`            |
| Autenticação  | JWT Bearer Token      |

#### Restrições

- Campos obrigatórios: `email`, `username`, `password`.
- Regra de negócio: Usuários com função de `ROLE_STAFF` ou `ROLE_ADMIN` não podem criar tickets.

### GET `tickets/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | GET               |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket a ser recuperado.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do ticket a ser recuperado. |

### DELETE `tickets/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | DELETE            |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket a ser deletado.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do ticket a ser deletado. |

### PATCH `tickets/{id}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | PATCH             |
| Path          | `/tickets/{id}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket a ser atualizado.
- Regra de Negócio: Tickets com o status definido como `IN_PROGRESS`, `RESOLVED` ou `CLOSED` não podem ser atualizados.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do ticket a ser atualizado. |

### PATCH `tickets/{id}/status`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | PATCH             |
| Path          | `/tickets/{id}/status`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Campos Obrigatórios: `status`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `id`      | `integer` | Path          | Sim           | ID do ticket a ser atualizado. |

### GET `tickets/user/{userId}`

| Componente    | Valor             |
|---------------|-------------------|
| Método HTTP   | PATCH             |
| Path          | `/tickets/user/{userId}`   |
| Autenticação  | JWT Bearer Token  |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ter o ID igual ao `{userId}`.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `userId`      | `integer` | Path          | Sim           | ID do usuário. |

## Gerenciamento de Respostas dos Tickets

### GET `tickets/{ticketId}/reply`

| Componente    | Valor                         |
|---------------|-------------------------------|
| Método HTTP   | GET                           |
| Path          | `tickets/{ticketId}/reply`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket referente as respostas recuperadas.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `ticketId`      | `integer` | Path          | Sim           | ID do ticket das respostas recuperadas. |

### POST `tickets/{ticketId}/reply`

| Componente    | Valor                         |
|---------------|-------------------------------|
| Método HTTP   | POST                          |
| Path          | `/tickets/{ticketId}/reply`   |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket para cadastrar respostas.
- Regras de Negócio:
    - O usuário proprietário não pode responder diretamente seu próprio ticket.
    - O usuário proprietário não pode responder diretamente sua própria resposta.
    - Para respostas subsequentes o usuário autenticado deve ser o proprietário do ticket a ser respondido.
    - Não é possível responder um ticket com `status` definido como `RESOLVED` ou `CLOSED`.

#### Parâmetros da Rota

| Nome       | Tipo      | Localização   | Obrigatório   | Descrição     |
|------------|-----------|---------------|---------------|---------------|
| `ticketId` | `integer` | Path          | Sim           | ID do ticket a ser respondido. |

### GET `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                         |
|---------------|-------------------------------|
| Método HTTP   | GET                           |
| Path          | `tickets/{ticketId}/reply/{replyId}`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket referente a resposta recuperada.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `ticketId`      | `integer` | Path          | Sim           | ID do ticket. |
| `replyId`      | `integer` | Path          | Sim           | ID da resposta. |

### DELETE `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                         |
|---------------|-------------------------------|
| Método HTTP   | DELETE                        |
| Path          | `tickets/{ticketId}/reply/{replyId}`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket e da resposta a ser deletada.
- Regra de negócio: Uma resposta já respondida não pode ser deletada.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `ticketId`      | `integer` | Path          | Sim           | ID do ticket. |
| `replyId`      | `integer` | Path          | Sim           | ID da resposta. |

### PATH `tickets/{ticketId}/reply/{replyId}`

| Componente    | Valor                         |
|---------------|-------------------------------|
| Método HTTP   | Path                        |
| Path          | `tickets/{ticketId}/reply`    |
| Autenticação  | JWT Bearer Token              |

#### Restrições:

- Administradores: Somente usuários com a função `ROLE_STAFF` ou `ROLE_ADMIN`.
- Propetário: O usuário autenticado deve ser o proprietário do ticket e da resposta a ser atualizada.
- Regra de negócio: Uma resposta já respondida não pode ser atualizada.

#### Parâmetros da Rota

| Nome      | Tipo      | Localização   | Obrigatório   | Descrição     |
|-----------|-----------|---------------|---------------|---------------|
| `ticketId`      | `integer` | Path          | Sim           | ID do ticket. |
| `replyId`      | `integer` | Path          | Sim           | ID da resposta. |