CREATE TABLE horarios_disponiveis (
    id BIGSERIAL PRIMARY KEY,
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    professor_id BIGINT NOT NULL,
    CONSTRAINT fk_horario_professor FOREIGN KEY (professor_id) REFERENCES professores (id)
);