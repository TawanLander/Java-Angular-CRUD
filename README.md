# Java + Angular CRUD

CRUD completo de usuários, construído em etapas para registrar a minha evolução em **Java/Spring Boot + Angular**. O repositório reúne back-end e front-end e cresce por versões.

## Versões

| Versão | Foco | Status |
|---|---|---|
| **V1** | CRUD REST com soft delete, Flyway, Swagger e Docker | ✅ Back-end pronto |
| **V2** | Autenticação com JWT e erros padronizados | 🔜 Planejada |
| **V3** | Front-end em Angular consumindo a API | 🔜 Planejada |
| **V4** | Testes, CI com GitHub Actions e deploy | 🔜 Planejada |

O front-end em Angular vem depois do back-end e será adicionado a este mesmo repositório.

## Tecnologias

- Java 21 e Spring Boot 3
- Spring Web, Spring Data JPA e Bean Validation
- PostgreSQL com migrations via Flyway
- Documentação com OpenAPI/Swagger
- Docker e Docker Compose (banco de dados e back-end), com build multi-stage

## Funcionalidades (V1)

- Cadastro, consulta, atualização total (`PUT`) e parcial (`PATCH`) de usuários
- **Soft delete**: o usuário excluído fica no banco com `deleted_at` preenchido e pode ser restaurado
- **Hard delete** disponível apenas para usuários que já passaram pelo soft delete
- Listagem filtrável por estado: ativos, excluídos ou todos
- E-mail e telefone únicos, com `409 Conflict` em caso de duplicidade
- Validação dos dados de entrada, com `400 Bad Request` para dados inválidos

## Endpoints

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| `GET` | `/users?status=ACTIVE\|DELETED\|ALL` | Lista usuários (padrão: `ACTIVE`) | `200` |
| `GET` | `/users/{id}` | Busca um usuário por id | `200` |
| `POST` | `/users` | Cadastra um usuário | `201` |
| `PUT` | `/users/{id}` | Atualiza o usuário por completo | `204` |
| `PATCH` | `/users/{id}` | Atualiza o usuário parcialmente | `204` |
| `DELETE` | `/users/{id}` | Exclui o usuário (soft delete) | `204` |
| `PATCH` | `/users/{id}/restore` | Restaura um usuário excluído | `204` |
| `DELETE` | `/users/{id}/permanent` | Exclui definitivamente (só se já estiver excluído) | `204` |

O parâmetro `status` aceita valores em maiúsculo ou minúsculo (`ACTIVE` ou `active`).

A documentação interativa fica disponível no Swagger UI com a aplicação rodando: `http://localhost:8080/swagger-ui.html`.

## Como rodar

### Com Docker (recomendado)

**Pré-requisito:** Docker. Não é preciso ter Java nem Maven instalados.

1. Clone o repositório:

   ```bash
   git clone https://github.com/TawanLander/Java-Angular-CRUD.git
   cd Java-Angular-CRUD
   ```

2. Crie o arquivo de variáveis de ambiente a partir do exemplo e ajuste a senha:

   ```bash
   cp .env.example .env
   ```

3. Suba o banco e o back-end:

   ```bash
   docker compose up -d --build
   ```

A API fica em `http://localhost:8080` e as migrations do Flyway rodam sozinhas na inicialização. Para acompanhar os logs, use `docker compose logs -f backend`.

Para parar tudo: `docker compose down`. Para apagar também os dados do banco: `docker compose down -v`.

### Modo desenvolvimento (aplicação pela IDE)

**Pré-requisitos:** JDK 21, Maven e Docker.

1. Faça o passo 2 acima (criar o `.env`).
2. Suba só o banco:

   ```bash
   docker compose up -d postgres
   ```

3. Execute a aplicação (`BackEnd/src/main/java/lander/tawan/App.java`) pela IDE, com o diretório de trabalho na raiz do repositório para que o `.env` seja lido.

Não rode a aplicação pela IDE e o container `backend` ao mesmo tempo, pois os dois usam a mesma porta.

### Variáveis de ambiente

| Variável | Descrição | Exemplo |
|---|---|---|
| `SERVER_PORT` | Porta da aplicação | `8080` |
| `DB_HOST` | Host do PostgreSQL (no container, o compose usa `postgres`) | `localhost` |
| `DB_PORT` | Porta do PostgreSQL na sua máquina (entre containers é sempre `5432`) | `5432` |
| `DB_NAME` | Nome do banco | `postgres` |
| `DB_USER` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `me_altere` |
| `JPA_DDL_AUTO` | Modo do Hibernate (`validate`, pois o schema vem do Flyway) | `validate` |
| `JPA_SHOW_SQL` | Exibir SQL no log | `false` |

## Estrutura

```
.
├── BackEnd/
│   ├── Dockerfile         # Build multi-stage (Maven -> JRE)
│   └── src/main/java/lander/tawan/
│       ├── config/        # Configuração do OpenAPI
│       ├── enums/         # Enums e converters
│       └── user/
│           ├── controller/  # UserApi (contrato e documentação) e UserController
│           ├── dto/         # Records de entrada e saída
│           ├── entity/      # Entidade JPA
│           ├── repository/  # Spring Data JPA
│           └── service/     # Regras de negócio
├── docker-compose.yml     # PostgreSQL e back-end
└── .env.example
```

## Decisões de projeto

- **Interface `UserApi` separada do controller:** concentra rotas e anotações do Swagger, e o `UserController` fica só com a lógica de entrada e saída.
- **Records como DTOs:** imutáveis e enxutos, com validação via Bean Validation.
- **Enum com converter para o filtro de listagem:** troca strings soltas por um tipo fixo e aceita maiúsculas e minúsculas na URL.
- **Schema controlado pelo Flyway:** o Hibernate só valida (`ddl-auto=validate`), e toda mudança no banco passa por uma migration versionada.
- **Configuração por variáveis de ambiente:** nenhum segredo vai para o repositório, só o `.env.example`.
- **Build multi-stage no Docker:** o JDK e o Maven ficam só na etapa de compilação, e a imagem final contém apenas o JRE e o `.jar`.

## Autor

**Tawan**, estudante de Análise e Desenvolvimento de Sistemas.