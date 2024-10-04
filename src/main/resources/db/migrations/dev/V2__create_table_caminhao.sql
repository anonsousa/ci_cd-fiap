CREATE TABLE tbl_caminhao (
    id_caminhao UUID PRIMARY KEY,
    motorista_id UUID,
    placa VARCHAR(20) UNIQUE,
    modelo VARCHAR(80),
    renavam VARCHAR(20) UNIQUE,
    capacidade NUMERIC
);