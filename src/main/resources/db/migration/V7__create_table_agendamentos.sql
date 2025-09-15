CREATE TABLE agendamentos (
    id SERIAL PRIMARY KEY,
    aluno_id BIGINT REFERENCES alunos(id),
    professor_id BIGINT REFERENCES professores(id),
    horario_disponivel_id BIGINT REFERENCES horarios_disponiveis(id),
    data_agendamento TIMESTAMP,
    agendado BOOLEAN
);
