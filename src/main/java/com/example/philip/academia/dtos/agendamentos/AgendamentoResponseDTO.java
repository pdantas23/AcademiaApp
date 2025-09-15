package com.example.philip.academia.dtos.agendamentos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoResponseDTO {
    private String nomeProfessor;
    private DayOfWeek diaDaSemana;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
}
