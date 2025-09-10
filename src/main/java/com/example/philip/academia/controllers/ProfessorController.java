package com.example.philip.academia.controllers;

import com.example.philip.academia.dtos.professores.ProfessorDTO;
import com.example.philip.academia.dtos.ResponseDTO;
import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseDTO<Void>> cadastrarProfessor(@RequestBody @Valid ProfessorDTO professorDTO) {
        professorService.cadastrarProfessor(professorDTO);

        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Professor cadastrado com sucesso!",
                HttpStatus.OK,
                null
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseDTO<ProfessorDTO>> buscarPorId(@PathVariable Long id) {
        ProfessorDTO professorDTO = professorService.buscarPorId(id);

        ResponseDTO<ProfessorDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Professor encontrado!",
                HttpStatus.OK,
                professorDTO
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseDTO<ProfessorDTO>> atualizarPorId(@PathVariable Long id, @RequestBody @Valid ProfessorDTO professorDTO) {
        ProfessorDTO atualizado = professorService.atualizarPorId(id, professorDTO);

        ResponseDTO<ProfessorDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Dados do professor atualizados com sucesso!",
                HttpStatus.OK,
                atualizado
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseDTO<ProfessorDTO>> deletarPorId(@PathVariable Long id) {
        professorService.deletarPorId(id);

        ResponseDTO<ProfessorDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Professor deletado com sucesso.",
                HttpStatus.OK,
                null
        );
        return ResponseEntity.ok(responseDTO);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/listar")
    public ResponseEntity<ResponseDTO<List<ProfessorDTO>>> listarTodos() {
        List<ProfessorDTO> professores = professorService.listarTodos();

        ResponseDTO<List<ProfessorDTO>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Lista de professores encontrada!",
                HttpStatus.OK,
                professores
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/listar/{especialidade}")
    public ResponseEntity<ResponseDTO<List<ProfessorDTO>>> listarPorEspecialidade(@PathVariable Especializacao especialidade) {
        List<ProfessorDTO> list = professorService.listarPorEspecialidade(especialidade);

        ResponseDTO<List<ProfessorDTO>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                ("Professores com especialidade em: " + especialidade),
                HttpStatus.OK,
                list
        );
        return ResponseEntity.ok(responseDTO);
    }
}
