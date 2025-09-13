package com.example.philip.academia.dtos.horarios;

import com.example.philip.academia.enums.horarios.PeriodoDia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HorarioResponseDTO {
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private PeriodoDia periodo;
}

