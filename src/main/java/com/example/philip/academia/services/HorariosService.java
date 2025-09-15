package com.example.philip.academia.services;

import com.example.philip.academia.dtos.horarios.HorarioDisponivelDTO;
import com.example.philip.academia.dtos.horarios.HorarioResponseDTO;
import com.example.philip.academia.enums.horarios.PeriodoDia;
import com.example.philip.academia.models.HorarioDisponivelModel;
import com.example.philip.academia.models.ProfessorModel;
import com.example.philip.academia.repositories.ProfessorRepository;
import com.example.philip.academia.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedHashMap;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HorariosService {
    @Autowired
    private ProfessorRepository professorRepository;

    public Map<DayOfWeek, List<HorarioResponseDTO>> definirHorarios(CustomUserDetails userDetails, List<HorarioDisponivelDTO> horariosDTO) {
        ProfessorModel professor = professorRepository.findByUserEmail(userDetails.getEmail())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        professor.getHorarios().clear();
        List<HorarioDisponivelModel> horarios = new ArrayList<>();

        for (HorarioDisponivelDTO dto : horariosDTO) {
            LocalTime inicio = dto.getHoraInicio();
            LocalTime fim = dto.getHoraFim();
            Duration duracaoSlot = Duration.ofMinutes(60);

            while (inicio.plus(duracaoSlot).compareTo(fim) <= 0) {
                LocalTime slotInicio = inicio;
                LocalTime slotFim = inicio.plus(duracaoSlot);

                boolean sobreposto = horarios.stream()
                        .anyMatch(h -> h.getDiaDaSemana() == dto.getDiaDaSemana() &&
                                h.getHoraInicio().isBefore(slotFim) &&
                                slotInicio.isBefore(h.getHoraFim()));
                if (sobreposto) {
                    throw new RuntimeException("Horário sobreposto: " + dto.getDiaDaSemana() + " " + slotInicio + " - " + slotFim);
                }

                HorarioDisponivelModel horario = new HorarioDisponivelModel();
                horario.setDiaDaSemana(dto.getDiaDaSemana());
                horario.setHoraInicio(slotInicio);
                horario.setHoraFim(slotFim);
                horario.setProfessor(professor);

                horarios.add(horario);
                inicio = slotFim;
            }
        }

        professor.getHorarios().addAll(horarios);
        professorRepository.save(professor);

        return professor.getHorarios().stream()
                .collect(Collectors.groupingBy(
                        HorarioDisponivelModel::getDiaDaSemana,
                        LinkedHashMap::new,
                        Collectors.mapping(h -> new HorarioResponseDTO(
                                h.getHoraInicio(),
                                h.getHoraFim(),
                                PeriodoDia.fromHora(h.getHoraInicio())
                        ), Collectors.toList())
                ));
    }

    public Map<DayOfWeek, List<HorarioResponseDTO>> listarHorariosAgrupados(Long professorId) {
        ProfessorModel professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        return professor.getHorarios().stream()
                .collect(Collectors.groupingBy(
                        HorarioDisponivelModel::getDiaDaSemana,
                        LinkedHashMap::new,
                        Collectors.mapping(h -> new HorarioResponseDTO(
                                h.getHoraInicio(),
                                h.getHoraFim(),
                                PeriodoDia.fromHora(h.getHoraInicio())
                        ), Collectors.toList())
                ));
    }
}
