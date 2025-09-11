CREATE TABLE pagamentos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    plano_id BIGINT NOT NULL,
    valor_pago NUMERIC(10,2),
    data_pagamento DATE,
    validade DATE,
    status_pagamento VARCHAR(50),
    bandeira_cartao VARCHAR(50),
    renovacao_automatica BOOLEAN,
    CONSTRAINT fk_pagamento_usuario FOREIGN KEY (usuario_id) REFERENCES users(id),
    CONSTRAINT fk_pagamento_plano FOREIGN KEY (plano_id) REFERENCES planos(id)
);