package com.example.philip.academia.controllers;

import com.example.philip.academia.dtos.ResponseDTO;
import com.example.philip.academia.dtos.agendamentos.AgendamentoDTO;
import com.example.philip.academia.dtos.agendamentos.AgendamentoResponseDTO;
import com.example.philip.academia.models.HorarioDisponivelModel;
import com.example.philip.academia.security.CustomUserDetails;
import com.example.philip.academia.services.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    @Autowired
    private AgendamentoService agendamentoService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/agendar")
    public ResponseEntity<ResponseDTO<Void>> marcarHorario(@AuthenticationPrincipal CustomUserDetails user, @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        agendamentoService.marcarHorario(user, agendamentoDTO);

        ResponseDTO<Void> responseDTO = new ResponseDTO<Void>(
                LocalDateTime.now(),
                "Agendamento feito com sucesso!",
                HttpStatus.CREATED,
                null
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/meus-agendamentos")
    public ResponseEntity<ResponseDTO<List<AgendamentoResponseDTO>>> listarAgendamentos(@AuthenticationPrincipal CustomUserDetails user) {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentosDoUsuario(user);

        ResponseDTO<List<AgendamentoResponseDTO>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Agendamentos carregados com sucesso!",
                HttpStatus.OK,
                agendamentos
        );
        return ResponseEntity.ok(responseDTO);
    }
}
