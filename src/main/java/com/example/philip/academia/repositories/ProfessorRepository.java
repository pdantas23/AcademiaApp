package com.example.philip.academia.repositories;

import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.models.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long> {
     List<ProfessorModel> findByEspecialidade(Especializacao especialidade);

    Optional<ProfessorModel> findByUserEmail(String email);

}
