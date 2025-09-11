package com.example.philip.academia.dtos.planos;

import com.example.philip.academia.enums.pagamentos.BandeiraCartao;
import com.example.philip.academia.enums.planos.TipoPlano;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanoResponseDTO {
    private TipoPlano tipoPlano;
    private LocalDate dataAssinatura;
    private LocalDate validade;
    private Boolean renovacaoAutomatica;
    private BandeiraCartao bandeiraCartao;
}
