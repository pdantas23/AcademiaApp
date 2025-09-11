package com.example.philip.academia.repositories;

import com.example.philip.academia.enums.planos.TipoPlano;
import com.example.philip.academia.models.PlanoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoRepository extends JpaRepository<PlanoModel, Long> {
   Optional<PlanoModel> findByTipo(TipoPlano tipo);
}
