package com.example.philip.academia.models;

import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.enums.professores.Formacao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "A especialidade é obrigatória.")
    private Especializacao especialidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "A formação é obrigatória.")
    private Formacao formacao;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HorarioDisponivelModel> horarios;

}
