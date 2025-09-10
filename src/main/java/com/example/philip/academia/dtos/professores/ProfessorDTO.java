package com.example.philip.academia.dtos.professores;

import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.enums.professores.Formacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfessorDTO {
    private Long id;

    @NotBlank(message = "O nome do usuário precisa ser preenchido.")
    private String username;

    @NotBlank(message = "A senha precisa ser preenchida.")
    private String password;

    @Email(message = "O email deve ser válido.")
    @NotBlank(message = "O email precisa ser preenchido.")
    private String email;

    @NotBlank(message = "O CPF precisa ser preenchido.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    private String cpf;

    @NotNull(message = "A especialidade é obrigatória.")
    private Especializacao especialidade;

    @NotNull(message = "A formação é obrigatória.")
    private Formacao formacao;
}
