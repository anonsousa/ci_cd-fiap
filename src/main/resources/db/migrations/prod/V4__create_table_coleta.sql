CREATE TABLE tbl_coleta (
                            id_coleta UUID PRIMARY KEY,
                            cep VARCHAR(12),
                            numero_casa VARCHAR(8),
                            tipos_residuo VARCHAR(80),
                            volume_peso NUMERIC,
                            id_caminhao UUID,
                            status VARCHAR(50),
                            informacoes_adicionais VARCHAR(255),
                            data_coleta DATE,
                            CONSTRAINT fk_caminhao_id FOREIGN KEY (id_caminhao) REFERENCES tbl_caminhao(id_caminhao) ON DELETE SET NULL
);
