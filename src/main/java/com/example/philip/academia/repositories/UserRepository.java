package com.example.philip.academia.repositories;

import com.example.philip.academia.enums.UserRole;
import com.example.philip.academia.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByCpf(String cpf);

    boolean existsByRole(UserRole userRole);
}
