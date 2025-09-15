package com.example.philip.academia.dtos.agendamentos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoDTO {
    @NotNull(message = "O dia do agendamento deve ser definido.")
    private DayOfWeek diaEscolhido;

    @NotNull(message = "A hora do agendamento deve ser definida.")
    private LocalTime horaEscolhida;

    @NotNull(message = "A ID do professor deve ser definido.")
    private Long professorId;
}
