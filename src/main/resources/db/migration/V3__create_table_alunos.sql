CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    plano_id BIGINT NOT NULL REFERENCES planos(id),
    data_assinatura DATE NOT NULL
);
