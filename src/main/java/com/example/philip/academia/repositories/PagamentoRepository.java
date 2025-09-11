package com.example.philip.academia.repositories;

import com.example.philip.academia.models.PagamentoModel;
import com.example.philip.academia.models.PlanoModel;
import com.example.philip.academia.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {
    Optional<PagamentoModel> findByAlunoAndPlano(UserModel aluno, PlanoModel plano);
}
