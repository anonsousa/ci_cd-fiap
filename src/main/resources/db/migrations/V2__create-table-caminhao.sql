CREATE TABLE tbl_caminhao (
    id_caminhao RAW(16) PRIMARY KEY,
    motorista_id RAW(16),
    placa VARCHAR2(20) UNIQUE,
    modelo VARCHAR2(80),
    renavam VARCHAR2(20) UNIQUE,
    tipo_residuo VARCHAR2(80),
    capacidade NUMBER,
    CONSTRAINT fk_motorista_id FOREIGN KEY (motorista_id) REFERENCES tbl_motorista(id_motorista)
);