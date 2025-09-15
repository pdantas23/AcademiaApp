package com.example.philip.academia.repositories;

import com.example.philip.academia.models.AlunoModel;
import com.example.philip.academia.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {
    Optional<AlunoModel> findByUser(UserModel usuario);
    Optional<AlunoModel> findByUserEmail(String email);
}
