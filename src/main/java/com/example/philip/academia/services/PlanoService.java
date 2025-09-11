package com.example.philip.academia.services;

import com.example.philip.academia.dtos.planos.PlanoResponseDTO;
import com.example.philip.academia.dtos.planos.PlanoDTO;
import com.example.philip.academia.enums.pagamentos.StatusPagamento;
import com.example.philip.academia.exceptionhandler.exceptions.EventNotFoundException;
import com.example.philip.academia.models.AlunoModel;
import com.example.philip.academia.models.PagamentoModel;
import com.example.philip.academia.models.PlanoModel;
import com.example.philip.academia.models.UserModel;
import com.example.philip.academia.repositories.AlunoRepository;
import com.example.philip.academia.repositories.PagamentoRepository;
import com.example.philip.academia.repositories.PlanoRepository;
import com.example.philip.academia.repositories.UserRepository;
import com.example.philip.academia.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PlanoService {
    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public PlanoResponseDTO assinarPlano(CustomUserDetails userDetails, PlanoDTO planoDTO) {
        UserModel usuario = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new EventNotFoundException("Usuário não encontrado."));

        PlanoModel plano = planoRepository.findByTipo(planoDTO.getTipoPlano())
                .orElseThrow(() -> new EventNotFoundException("Plano não encontrado."));

        // Busca o aluno associado ao usuário, se existir
        AlunoModel aluno = alunoRepository.findByUser(usuario).orElse(null);

        if (aluno != null) {
            // Verifica se já existe um plano ativo
            if (aluno.getPlano() != null
                    && aluno.getDataAssinatura().plusDays(aluno.getPlano().getDuracaoEmDias()).isAfter(LocalDate.now())) {
                throw new IllegalStateException("O usuário já possui um plano ativo.");
            }
        } else {
            // Se não existir, cria um novo aluno
            aluno = new AlunoModel();
            aluno.setUser(usuario);
        }

        // Cria o pagamento
        PagamentoModel pagamento = new PagamentoModel(
                null,
                LocalDate.now(),
                null,
                plano.getValor(),
                StatusPagamento.APROVADO,
                planoDTO.getBandeiraCartao(),
                planoDTO.getRenovacaoAutomatica(),
                plano,
                usuario
        );

        LocalDate validade = pagamento.calcularValidade();
        pagamento.setValidade(validade);
        pagamentoRepository.save(pagamento);

        // Atualiza o aluno com o novo plano
        aluno.setPlano(plano);
        aluno.setDataAssinatura(LocalDate.now());
        alunoRepository.save(aluno);

        return new PlanoResponseDTO(
                plano.getTipo(),
                LocalDate.now(),
                validade,
                planoDTO.getRenovacaoAutomatica(),
                null
        );
    }
}
