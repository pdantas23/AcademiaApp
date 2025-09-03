package com.example.philip.academia.dtos.users;

import com.example.philip.academia.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleUpdateDTO {
    @NotNull(message = "O ID do usuário não pode estar em branco.")
    private Long userId;

    @NotNull(message = "A role não pode estar em branco")
    private UserRole role;
}
