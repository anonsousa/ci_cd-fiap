ALTER TABLE tbl_caminhao
    ADD CONSTRAINT fk_motorista_id FOREIGN KEY (motorista_id) REFERENCES tbl_motorista (id_motorista);
