CREATE TABLE tbl_motorista (
    id_motorista RAW(16) PRIMARY KEY,
    nome VARCHAR2(120),
    email VARCHAR2(120) UNIQUE,
    telefone VARCHAR2(20),
    carteira_habilitacao VARCHAR2(20) UNIQUE,
    data_cadastro DATE
);