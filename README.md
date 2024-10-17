### Documentação da API de Autenticação e Inteligência Artificial

#### Visão Geral
A API fornece endpoints para autenticação de usuários e geração de conteúdo através de modelos de IA. Os principais recursos incluem registro, login, visualização de perfil, e geração de imagens e textos.

#### Roteamento e Métodos

##### 1. Autenticação e Gerenciamento de Usuários

- **POST /api/images/register**
  - **Descrição**: Registra um novo usuário.
  - **Corpo da solicitação**:
    ```json
    {
      "username": "string",
      "password": "string",
      "name": "string",
      "email": "string"
    }
    ```
  - **Resposta**:
    - **Status 201 Created**: Retorna os dados do usuário registrado.
    - **Status 400 Bad Request**: Retorna uma mensagem de erro se os dados fornecidos forem inválidos.

- **POST /api/images/login**
  - **Descrição**: Realiza o login de um usuário.
  - **Corpo da solicitação**:
    ```json
    {
      "username": "string",
      "password": "string"
    }
    ```
  - **Resposta**:
    - **Status 200 OK**: Retorna um token JWT.
    - **Status 400 Bad Request**: Retorna uma mensagem de erro se as credenciais forem inválidas.

- **GET /api/images/profile**
  - **Descrição**: Obtém os dados do perfil do usuário autenticado.
  - **Cabeçalhos**:
    - `Authorization: Bearer <token>`
  - **Resposta**:
    - **Status 200 OK**: Retorna os dados do usuário.
    - **Status 401 Unauthorized**: Retorna uma mensagem de erro se o usuário não estiver autenticado.

##### 2. Interação com IA

- **GET /api/images/version**
  - **Descrição**: Retorna a versão da API.
  - **Resposta**:
    - **Status 200 OK**: Retorna a versão da API.

- **POST /api/images/generateImage**
  - **Descrição**: Gera uma imagem a partir de um prompt de texto.
  - **Corpo da solicitação**:
    ```plaintext
    string
    ```
  - **Resposta**:
    - **Status 200 OK**: Retorna a imagem gerada no formato `image/png`.
    - **Status 500 Internal Server Error**: Retorna uma mensagem de erro se a geração falhar.

- **POST /api/images/generateText**
  - **Descrição**: Gera uma resposta de texto a partir de um prompt de texto.
  - **Corpo da solicitação**:
    ```plaintext
    string
    ```
  - **Resposta**:
    - **Status 200 OK**: Retorna a resposta de texto gerada.
    - **Status 500 Internal Server Error**: Retorna uma mensagem de erro se a geração falhar.

#### Exemplos de Uso

##### Registro de Usuário

```bash
curl -X POST http://localhost:8080/api/images/register \
-H "Content-Type: application/json" \
-d '{
  "username": "john_doe",
  "password": "secure_password",
  "name": "John Doe",
  "email": "john@example.com"
}'
```

##### Login de Usuário

```bash
curl -X POST http://localhost:8080/api/images/login \
-H "Content-Type: application/json" \
-d '{
  "username": "john_doe",
  "password": "secure_password"
}'
```

##### Visualização de Perfil

```bash
curl -X GET http://localhost:8080/api/images/profile \
-H "Authorization: Bearer <token>"
```

##### Geração de Imagem

```bash
curl -X POST http://localhost:8080/api/images/generateImage \
-H "Content-Type: text/plain" \
-d 'Uma bela paisagem'
```

##### Geração de Texto

```bash
curl -X POST http://localhost:8080/api/images/generateText \
-H "Content-Type: text/plain" \
-d 'Escreva um poema sobre a natureza'
```

#### Segurança
- **Autenticação**: Todos os endpoints protegidos requerem autenticação via token JWT.
- **Permissões**: O usuário deve estar autenticado para acessar o perfil.

#### Considerações Finais
- **Handing de Erros**: A API retorna códigos de status HTTP apropriados e mensagens de erro claras.
- **Melhores Práticas**: Use sempre HTTPS para garantir a segurança das comunicações.
