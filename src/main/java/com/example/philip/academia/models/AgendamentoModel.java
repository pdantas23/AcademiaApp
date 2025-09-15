package com.example.philip.academia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AlunoModel aluno;

    @ManyToOne
    private ProfessorModel professor;

    @ManyToOne
    private HorarioDisponivelModel horarioDisponivel;

    private LocalDateTime dataAgendamento;
    private boolean agendado;
}
