package com.example.philip.academia.models;

import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.enums.professores.Formacao;
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
@Table(name = "professores")
public class ProfessorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    @NotBlank(message = "A especialidade é obrigatória.")
    private Especializacao especialidade;

    @Column(nullable = false)
    @NotBlank(message = "A formação é obrigatória.")
    private Formacao formacao;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivelModel> horarios;

}
