CREATE TABLE professores (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    formacao VARCHAR(150) NOT NULL,
    CONSTRAINT fk_professor_usuario FOREIGN KEY (usuario_id) REFERENCES users (id)
);