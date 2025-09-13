package com.example.philip.academia.enums.horarios;

import java.time.LocalTime;

public enum PeriodoDia {
    MANHA,
    TARDE,
    NOITE;

    public static PeriodoDia fromHora(LocalTime hora) {
        if (hora.isBefore(LocalTime.of(12, 0))) {
            return MANHA;
        } else if (hora.isBefore(LocalTime.of(18, 0))) {
            return TARDE;
        } else {
            return NOITE;
        }
    }
}
