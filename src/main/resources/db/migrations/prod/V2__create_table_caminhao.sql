CREATE TABLE tbl_caminhao (
                              id_caminhao UUID PRIMARY KEY,
                              motorista_id UUID REFERENCES tbl_motorista(id_motorista),
                              placa VARCHAR(20) UNIQUE NOT NULL,
                              modelo VARCHAR(80),
                              renavam VARCHAR(20) UNIQUE NOT NULL,
                              capacidade NUMERIC CHECK (capacidade >= 0)
);