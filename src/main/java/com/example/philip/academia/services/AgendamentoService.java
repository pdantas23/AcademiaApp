package com.example.philip.academia.services;

import com.example.philip.academia.dtos.agendamentos.AgendamentoDTO;
import com.example.philip.academia.dtos.agendamentos.AgendamentoResponseDTO;
import com.example.philip.academia.exceptionhandler.exceptions.DuplicateResourceException;
import com.example.philip.academia.models.*;
import com.example.philip.academia.repositories.AgendamentoRepository;
import com.example.philip.academia.repositories.AlunoRepository;
import com.example.philip.academia.repositories.ProfessorRepository;
import com.example.philip.academia.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService{
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public void marcarHorario(CustomUserDetails userDetails, AgendamentoDTO agendamentoDTO) {
        // Busca do professor
        ProfessorModel professor = professorRepository.findById(agendamentoDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        // Busca do aluno pelo email (login)
        AlunoModel aluno = alunoRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("O usuário não é um aluno."));

        // Busca do horário disponível do professor
        HorarioDisponivelModel horario = professor.getHorarios().stream()
                .filter(h -> h.getDiaDaSemana() == agendamentoDTO.getDiaEscolhido()
                        && h.getHoraInicio().equals(agendamentoDTO.getHoraEscolhida()))
                .findFirst()
                .orElseThrow(() -> new DuplicateResourceException("Horário não encontrado para esse professor."));

        // Calcula a próxima ocorrência do dia escolhido
        LocalDate proximoDiaEscolhido = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(agendamentoDTO.getDiaEscolhido()));
        LocalDateTime dataHorarioEscolhido = LocalDateTime.of(proximoDiaEscolhido, agendamentoDTO.getHoraEscolhida());

        // Validação: não permite agendar no passado
        if (dataHorarioEscolhido.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar um horário no passado.");
        }

        // Verifica se o horário já está agendado
        boolean agendado = agendamentoRepository.existsByProfessorAndHorarioDisponivel(professor, horario);
        if (agendado) {
            throw new DuplicateResourceException("Esse horário já foi agendado.");
        }

        // Criação do agendamento
        AgendamentoModel agendamento = new AgendamentoModel(
                null,
                aluno,
                professor,
                horario,
                dataHorarioEscolhido,
                true
        );
        agendamentoRepository.save(agendamento);
    }


    public List<AgendamentoResponseDTO> listarAgendamentosDoUsuario(CustomUserDetails user) {

        return agendamentoRepository.findByAlunoUserEmail(user.getEmail())
                .stream()
                .map(a -> new AgendamentoResponseDTO(
                        a.getProfessor().getUser().getUsername(), // ajuste para o campo real
                        a.getHorarioDisponivel().getDiaDaSemana(),
                        a.getDataAgendamento().toLocalDate(),
                        a.getHorarioDisponivel().getHoraInicio()
                ))
                .collect(Collectors.toList());
    }
}
