package com.example.philip.academia.controllers;

import com.example.philip.academia.dtos.planos.PlanoResponseDTO;
import com.example.philip.academia.dtos.planos.PlanoDTO;
import com.example.philip.academia.dtos.ResponseDTO;
import com.example.philip.academia.security.CustomUserDetails;
import com.example.philip.academia.services.PlanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/planos")
public class PlanoController {
    @Autowired
    private PlanoService planoService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/assinar")
    public ResponseEntity<ResponseDTO<PlanoResponseDTO>> assinarPlano(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody PlanoDTO planoDTO){
        PlanoResponseDTO plano = planoService.assinarPlano(userDetails, planoDTO);

        ResponseDTO<PlanoResponseDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Plano assinado com sucesso!",
                HttpStatus.OK,
                plano
        );
        return ResponseEntity.ok(responseDTO);
    }
}
