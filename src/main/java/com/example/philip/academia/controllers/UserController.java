package com.example.philip.academia.controllers;

import com.example.philip.academia.dtos.ResponseDTO;
import com.example.philip.academia.dtos.users.LoginDTO;
import com.example.philip.academia.dtos.users.RoleUpdateDTO;
import com.example.philip.academia.dtos.users.UserDTO;
import com.example.philip.academia.models.UserModel;
import com.example.philip.academia.services.AuthService;
import com.example.philip.academia.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseDTO<Void>> cadastrarUsuario(@RequestBody @Valid UserDTO user) {
        userService.cadastrarUsuario(user);

        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Usuário cadastrado com sucesso!",
                HttpStatus.OK,
                null
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> logarUsuario(@RequestBody @Valid LoginDTO userLogin) {
        String token = authService.logarUsuario(userLogin);

        ResponseDTO<String> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Usuário logado com sucesso!",
                HttpStatus.OK,
                token
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/role")
    public ResponseEntity<ResponseDTO<Void>> alterarRoleUsuario(@RequestBody @Valid RoleUpdateDTO user) {
        UserModel usuario = userService.alterarRole(user);

        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                ("Role alterada com sucesso! Usuário: " + usuario.getUsername() + ". Nova role: " + usuario.getRole()),
                HttpStatus.OK,
                null
        );
        return ResponseEntity.ok(responseDTO);
    }
}
