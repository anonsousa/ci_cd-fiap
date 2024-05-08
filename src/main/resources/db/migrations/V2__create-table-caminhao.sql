CREATE TABLE tbl_caminhao (
    id_caminhao RAW(16) PRIMARY KEY,
    motorista_id RAW(16),
    placa VARCHAR2(255) UNIQUE,
    modelo VARCHAR2(255),
    renavam VARCHAR2(255) UNIQUE,
    tipo_residuo VARCHAR2(255),
    bairro_coleta VARCHAR2(255),
    capacidade NUMBER,
    CONSTRAINT fk_motorista_id FOREIGN KEY (motorista_id) REFERENCES tbl_motorista(id_motorista)
);