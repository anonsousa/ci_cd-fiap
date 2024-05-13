CREATE TABLE tbl_coleta (
    id_coleta RAW(16) PRIMARY KEY,
    cep VARCHAR2(12),
    numero_casa VARCHAR2(8),
    tipos_residuo VARCHAR2(80),
    volume_peso NUMBER,
    id_caminhao RAW(16),
    status VARCHAR2(50),
    informacoes_adicionais VARCHAR2(255),
    data_coleta DATE,
    CONSTRAINT fk_caminhao_id FOREIGN KEY (id_caminhao) REFERENCES tbl_caminhao(id_caminhao)
);