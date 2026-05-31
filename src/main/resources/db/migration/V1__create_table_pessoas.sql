CREATE TABLE pessoas (
    id               BIGSERIAL PRIMARY KEY,
    nome_completo    VARCHAR(255)  NOT NULL,
    documento_cpf    VARCHAR(14)   NOT NULL UNIQUE,
    email            VARCHAR(255)  NOT NULL UNIQUE,
    data_nascimento  DATE          NOT NULL,
    cep              VARCHAR(9)    NOT NULL,
    logradouro       VARCHAR(255)  NOT NULL,
    complemento      VARCHAR(255),
    bairro           VARCHAR(255)  NOT NULL,
    cidade           VARCHAR(255)  NOT NULL,
    estado           VARCHAR(2)    NOT NULL,
    login            VARCHAR(7)    NOT NULL UNIQUE
);