CREATE TABLE planos (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL UNIQUE,
    valor NUMERIC(10,2) NOT NULL,
    duracao_em_dias INT
);

INSERT INTO planos (tipo, valor, duracao_em_dias) VALUES
('MENSAL', 100.00, 30),
('TRIMESTRAL', 270.00, 90),
('ANUAL', 900.00, 365),
('SEM_PLANO', 0, NULL);