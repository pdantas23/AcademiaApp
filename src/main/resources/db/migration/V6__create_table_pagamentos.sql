CREATE TABLE pagamentos (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    plano_id BIGINT NOT NULL,
    valor NUMERIC(10,2) NOT NULL,
    data_pagamento TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pagamento_usuario FOREIGN KEY (usuario_id) REFERENCES users (id),
    CONSTRAINT fk_pagamento_plano FOREIGN KEY (plano_id) REFERENCES planos (id)
);