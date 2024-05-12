CREATE TABLE tbl_users (
    user_id RAW(16) PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    senha VARCHAR2(100) NOT NULL,
    role VARCHAR2(50) DEFAULT 'USER'
);