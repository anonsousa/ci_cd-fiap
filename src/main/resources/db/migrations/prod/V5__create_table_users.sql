CREATE TABLE tbl_users (
                           user_id UUID PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           senha VARCHAR(100) NOT NULL,
                           role VARCHAR(50) NOT NULL DEFAULT 'USER'
);