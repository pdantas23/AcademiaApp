package com.example.philip.academia.controllers;

import com.example.philip.academia.dtos.ResponseDTO;
import com.example.philip.academia.dtos.horarios.HorarioDisponivelDTO;
import com.example.philip.academia.dtos.horarios.HorarioResponseDTO;
import com.example.philip.academia.models.HorarioDisponivelModel;
import com.example.philip.academia.security.CustomUserDetails;
import com.example.philip.academia.services.HorariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/horarios")
@RestController
public class HorariosController {

    @Autowired
    private HorariosService horariosService;

    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping("/determinar")
    public ResponseEntity<ResponseDTO<Map<DayOfWeek, List<HorarioResponseDTO>>>> determinarHorarios(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody List<HorarioDisponivelDTO> horariosDTO) {

        Map<DayOfWeek, List<HorarioResponseDTO>> horarios = horariosService.definirHorarios(userDetails, horariosDTO);

        ResponseDTO<Map<DayOfWeek, List<HorarioResponseDTO>>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Horários definidos com sucesso!",
                HttpStatus.CREATED,
                horarios
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/listar/{id}")
    public ResponseEntity<ResponseDTO<Map<DayOfWeek, List<HorarioResponseDTO>>>> listarHorarios(@PathVariable Long id) {
        Map<DayOfWeek, List<HorarioResponseDTO>> horariosAgrupados = horariosService.listarHorariosAgrupados(id);

        ResponseDTO<Map<DayOfWeek, List<HorarioResponseDTO>>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Horários recuperados com sucesso!",
                HttpStatus.OK,
                horariosAgrupados
        );
        return ResponseEntity.ok(responseDTO);
    }
}
