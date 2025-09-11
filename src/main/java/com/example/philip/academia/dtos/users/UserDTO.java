package com.example.philip.academia.dtos.users;

import com.example.philip.academia.enums.usuarios.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotBlank(message = "O nome do usuário precisa ser preenchido.")
    private String username;

    @NotBlank(message = "A senha precisa ser preenchida.")
    private String password;

    @Email(message = "O email deve ser válido.")
    @NotBlank(message = "O email precisa ser preenchido.")
    private String email;

    @NotBlank(message = "O cpf precisa ser preenchido.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    private String cpf;

    private UserRole role;
}
