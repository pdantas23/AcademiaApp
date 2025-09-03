package com.example.philip.academia.enums.professores;

public enum Especializacao {
    // Fisioterapia
    FISIOTERAPIA_PELVICA(Formacao.FISIOTERAPIA),
    FISIOTERAPIA_RESPIRATORIA(Formacao.FISIOTERAPIA),

    // Educação Física
    PERSONAL_TRAINER(Formacao.EDUCACAO_FISICA),
    MUSCULACAO(Formacao.EDUCACAO_FISICA),
    TREINAMENTO_FUNCIONAL(Formacao.EDUCACAO_FISICA),

    // Nutrição
    NUTRICAO_ESPORTIVA(Formacao.NUTRICAO),
    NUTRICAO_CLINICA(Formacao.NUTRICAO);

    private final Formacao formacao;

    Especializacao(Formacao formacao) {
        this.formacao = formacao;
    }

    public Formacao getFormacao() {
        return formacao;
    }
}
