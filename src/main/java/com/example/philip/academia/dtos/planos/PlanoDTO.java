package com.example.philip.academia.dtos.planos;

import com.example.philip.academia.enums.pagamentos.BandeiraCartao;
import com.example.philip.academia.enums.planos.TipoPlano;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanoDTO {
    @NotNull(message = "O plano é obrigatório.")
    private TipoPlano tipoPlano;

    @NotNull(message = "A bandeira do cartão é obrigatória")
    private BandeiraCartao bandeiraCartao;

    @NotNull(message = "A renovação deve ser definida como automática ou não.")
    private Boolean renovacaoAutomatica;
}
