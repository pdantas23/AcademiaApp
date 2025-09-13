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

        // Limpa horários antigos
        professor.getHorarios().clear();

        List<HorarioDisponivelModel> horarios = new ArrayList<>();

        for (HorarioDisponivelDTO dto : horariosDTO) {

            // Verifica sobreposição com os horários já adicionados nesta lista
            boolean sobreposto = horarios.stream()
                    .anyMatch(h -> h.getDiaDaSemana() == dto.getDiaDaSemana() &&
                            h.getHoraInicio().isBefore(dto.getHoraFim()) &&
                            dto.getHoraInicio().isBefore(h.getHoraFim()));
            if (sobreposto) {
                throw new RuntimeException("Horário sobreposto: " + dto.getDiaDaSemana() + " " + dto.getHoraInicio() + " - " + dto.getHoraFim());
            }

            // Cria o horário
            HorarioDisponivelModel horario = new HorarioDisponivelModel();
            horario.setDiaDaSemana(dto.getDiaDaSemana());
            horario.setHoraInicio(dto.getHoraInicio());
            horario.setHoraFim(dto.getHoraFim());
            horario.setProfessor(professor);

            PeriodoDia periodo = PeriodoDia.fromHora(dto.getHoraInicio());
            System.out.println("Horario " + dto.getHoraInicio() + " pertence ao período " + periodo);

            horarios.add(horario);
        }

        professor.getHorarios().addAll(horarios);
        professorRepository.save(professor);

        // Retorna agrupado por dia da semana, igual ao listarHorariosAgrupados
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
