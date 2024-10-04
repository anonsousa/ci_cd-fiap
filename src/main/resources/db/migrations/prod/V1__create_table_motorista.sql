CREATE TABLE tbl_motorista (
                               id_motorista UUID PRIMARY KEY,
                               nome VARCHAR(120) NOT NULL,
                               email VARCHAR(120) UNIQUE NOT NULL,
                               telefone VARCHAR(20),
                               carteira_habilitacao VARCHAR(20) UNIQUE NOT NULL,
                               data_cadastro DATE NOT NULL DEFAULT CURRENT_DATE
);