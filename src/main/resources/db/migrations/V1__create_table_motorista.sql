CREATE TABLE tbl_motorista (
    id_motorista UUID PRIMARY KEY,
    nome VARCHAR(120),
    email VARCHAR(120) UNIQUE,
    telefone VARCHAR(20),
    carteira_habilitacao VARCHAR(20) UNIQUE,
    data_cadastro DATE
);