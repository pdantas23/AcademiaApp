CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    plano_id BIGINT NOT NULL REFERENCES planos(id),
    data_assinatura DATE NOT NULL,
    CONSTRAINT fk_aluno_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_aluno_plano FOREIGN KEY (plano_id) REFERENCES planos(id)
);
