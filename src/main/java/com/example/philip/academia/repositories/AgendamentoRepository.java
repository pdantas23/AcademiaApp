package com.example.philip.academia.repositories;
import com.example.philip.academia.models.AgendamentoModel;
import com.example.philip.academia.models.HorarioDisponivelModel;
import com.example.philip.academia.models.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
    boolean existsByProfessorAndHorarioDisponivel(ProfessorModel professor, HorarioDisponivelModel horarioDisponivel);
    List<AgendamentoModel> findByAlunoUserEmail(String email);
}
