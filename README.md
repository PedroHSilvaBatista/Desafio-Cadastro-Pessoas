# Cadastro de Pessoas — Back-end

API REST desenvolvida em **Java + Spring Boot** para o cadastro de pessoas com geração automática de login.

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.14
- Spring Web (API REST)
- Spring Data JPA + Hibernate
- Bean Validation
- Flyway (migrations)
- PostgreSQL
- Lombok

---

## Pré-requisitos

- Java 21+
- Maven
- PostgreSQL

---

## Como Rodar

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio-backend.git
```

Ou baixe o `.zip` pelo GitHub e extraia na pasta de sua preferência.

### 2. Banco de Dados

Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE cadastro_pessoas;
```

### 3. Variáveis de Ambiente

Configure as seguintes variáveis de ambiente no seu sistema ou IDE:

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DB_POSTGRESQL_HOST` | Host e porta do banco | `localhost:5432` |
| `DB_POSTGRESQL_NAME` | Nome do banco | `cadastro_pessoas` |
| `DB_POSTGRESQL_USERNAME` | Usuário do banco | `postgres` |
| `DB_POSTGRESQL_PASSWORD` | Senha do banco | `sua_senha` |

**No IntelliJ IDEA:** acesse `Run > Edit Configurations > Environment Variables` e adicione as variáveis acima.

### 4. Executar

**Via IntelliJ IDEA:** execute a classe principal `DesafioApplication.java` clicando no botão ▶ Run.

**Via terminal:**
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> O Flyway criará automaticamente a tabela `pessoas` ao iniciar a aplicação.

---

## Endpoints

### Health Check

```
GET /health-check
```

Verifica se a API está online. Utilizado pelo front-end na inicialização.

**Response 200 OK:**
```
Teste de integridade com a API ok!
```

---

### Cadastro de Pessoa

```
POST /api/v1/pessoas
```

**Request Body:**
```json
{
  "nomeCompleto": "Maria Silva Souza",
  "documentoCpf": "123.456.789-09",
  "email": "maria@email.com",
  "dataNascimento": "1990-05-20",
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "complemento": "lado ímpar",
  "bairro": "Sé",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "nomeCompleto": "Maria Silva Souza",
  "documentoCpf": "123.456.789-09",
  "email": "maria@email.com",
  "dataNascimento": "1990-05-20",
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "complemento": "lado ímpar",
  "bairro": "Sé",
  "cidade": "São Paulo",
  "estado": "SP",
  "login": "mariasi"
}
```

**Response 400 Bad Request — Validação:**
```json
{
  "title": "Erro de validação",
  "status": 400,
  "detail": "Um ou mais campos estão inválidos",
  "Details": [
    {
      "campo": "documentoCpf",
      "mensagem": "CPF deve estar no formato 123.456.789-09"
    }
  ]
}
```

**Response 400 Bad Request — CPF duplicado:**
```json
{
  "title": "O CPF inserido já foi cadastrado",
  "status": 400,
  "detail": "Já existe uma pessoa com o mesmo CPF cadastrado"
}
```

**Response 400 Bad Request — E-mail duplicado:**
```json
{
  "title": "O Email inserido já foi cadastrado",
  "status": 400,
  "detail": "Já existe uma pessoa com o mesmo e-mail cadastrado"
}
```

**Response 500 Internal Server Error — Falha no login:**
```json
{
  "title": "Houve um erro na geração do login de usuário",
  "status": 500,
  "detail": "Não foi possível gerar o login!"
}
```

---

## Estrutura do Projeto

```
src/main/java/br/com/pessoas/cadastro/desafio/
├── controller/
│   ├── PessoaController.java       # Endpoint de cadastro
│   └── HealthCheckController.java  # Endpoint de health check
├── service/
│   └── PessoaService.java          # Regras de negócio
├── repository/
│   └── PessoaRepository.java       # Acesso ao banco de dados
├── model/
│   ├── Pessoa.java                 # Entidade JPA
│   └── Estado.java                 # Enum com os 27 estados brasileiros
├── dto/
│   ├── PessoaRequest.java          # Dados de entrada com validações
│   └── PessoaResponse.java         # Dados de saída
├── mapper/
│   └── PessoaMapper.java           # Conversão entre DTO e entidade
├── exception/
│   ├── CPFValidacaoException.java
│   ├── EmailValidacaoException.java
│   └── LoginGeracaoException.java
├── error/
│   └── GlobalExceptionHandler.java # Tratamento centralizado de erros
├── config/
│   └── CorsConfiguration.java      # Configuração de CORS
└── util/
    ├── LoginGerador.java            # Lógica de geração de login
    └── NomeNormalizador.java        # Normalização do nome
```

---

## Decisões Técnicas

### Documento de identificação
Foi adotado o **CPF** como documento, no formato `123.456.789-09`. O formato é validado pelo tamanho (14 caracteres com máscara) tanto no front-end quanto no back-end.

### Persistência
Optou-se pelo **PostgreSQL** por ser um banco relacional robusto e amplamente utilizado no mercado, adequado para dados estruturados como cadastro de pessoas.

O schema é versionado pelo **Flyway**, garantindo rastreabilidade das alterações e facilitando a execução em diferentes ambientes sem necessidade de criar tabelas manualmente.

### Validações em camadas
- **Bean Validation** (`@Valid`) — valida os campos do `PessoaRequest` antes de chegar ao Service
- **Service** — valida regras de negócio como CPF e e-mail duplicados
- O `GlobalExceptionHandler` captura todas as exceções e retorna respostas padronizadas no formato **RFC 7807 (ProblemDetail)**

### Migrations vs anotações JPA
As restrições de integridade (`NOT NULL`, `UNIQUE`) foram definidas na migration SQL em vez de anotações `@Column` na entidade. Isso centraliza o controle do schema no Flyway, evitando divergências entre o código e o banco.

---

## Lógica de Geração do Login

O login é gerado automaticamente com **exatamente 7 caracteres**, apenas letras minúsculas (a-z), sem números e sem espaços, construído a partir do nome normalizado da pessoa.

### Normalização do nome
Antes da geração, o nome é normalizado pelo `NomeNormalizador`:
1. Substitui `ç` por `c` (cedilha não tratada pelo NFD)
2. Aplica normalização NFD — decompõe acentos em letra base + acento
3. Remove caracteres não ASCII — elimina os acentos decompostos
4. Converte para minúsculo
5. Remove espaços nas extremidades

### Estratégia 1 — Janela deslizante
Percorre o nome completo sem espaços extraindo substrings consecutivas de 7 caracteres até encontrar uma disponível.

**Exemplo:** `mariasilvasouza`
| Iteração | Candidato | Disponível |
|----------|-----------|------------|
| 1 | `mariasi` | ✅ usa esse |
| 2 | `ariasil` | usado se anterior existir |
| 3 | `riasilv` | e assim por diante... |

### Estratégia 2 — Fallback
Caso a janela deslizante se esgote, combina o **primeiro nome completo** com uma **janela deslizante sobre os sobrenomes concatenados**.

**Exemplo:** `Ana Clara Lima`
- `primeiroNome` = `ana` (3 chars)
- `sobrenomeJunto` = `claralima`
- `ponteiro` = 7 - 3 = 4

| Iteração | Candidato |
|----------|-----------|
| 1 | `anaclar` |
| 2 | `analara` |
| 3 | `anaaral` |

### Unicidade
Antes de qualquer iteração, todos os logins existentes são carregados do banco em um **HashSet** — garantindo verificações O(1) em memória e evitando múltiplas consultas ao banco durante o processo de geração.
