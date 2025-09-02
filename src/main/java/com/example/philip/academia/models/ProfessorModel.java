package com.example.philip.academia.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    @NotBlank(message = "A especialidade é obrigatória.")
    private String especialidade;

    @Column(nullable = false)
    @NotBlank(message = "A formação é obrigatória.")
    private String formacao;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivelModel> horarios;

    /*vamos criar enums com formacoes e algumas especialidades
    ligadas a ela (por exemplo um fisioterapeuta nao pode ter
            especializacao em algo de educacao fisica).*/
}
