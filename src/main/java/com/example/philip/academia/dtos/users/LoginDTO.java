package com.example.philip.academia.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "O email não é válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String password;
}
