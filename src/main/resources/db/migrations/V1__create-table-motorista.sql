CREATE TABLE tbl_motorista (
    idMotorista RAW(16) PRIMARY KEY,
    nome VARCHAR2(255),
    email VARCHAR2(255) UNIQUE,
    telefone VARCHAR2(20),
    senha VARCHAR2(255),
    carteiraHabilitacao VARCHAR2(20) UNIQUE,
    dataCadastro DATE
);