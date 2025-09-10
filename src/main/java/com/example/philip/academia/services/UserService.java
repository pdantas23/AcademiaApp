package com.example.philip.academia.services;

import com.example.philip.academia.dtos.users.RoleUpdateDTO;
import com.example.philip.academia.dtos.users.UserDTO;
import com.example.philip.academia.enums.UserRole;
import com.example.philip.academia.exceptionhandler.exceptions.DuplicateResourceException;
import com.example.philip.academia.exceptionhandler.exceptions.EventNotFoundException;
import com.example.philip.academia.exceptionhandler.exceptions.UnathorizedException;
import com.example.philip.academia.models.UserModel;
import com.example.philip.academia.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastrarUsuario(UserDTO userDTO) {
       userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(u -> {throw new DuplicateResourceException("Email já cadastrado");});

        userRepository.findByCpf(userDTO.getCpf())
                .ifPresent(u -> { throw new DuplicateResourceException("CPF já cadastrado");});

        UserModel usuario = new UserModel(
                null,
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getEmail(),
                userDTO.getCpf(),
                UserRole.ALUNO
        );
        userRepository.save(usuario);
    }


    public UserModel alterarRole(RoleUpdateDTO user) {
        UserModel usuario = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new EventNotFoundException("Usuário não encontrado"));

        // Verifica se a nova role é igual à atual
        if (usuario.getRole() == user.getRole()) {
            throw new UnathorizedException("A nova role é igual à atual. Nenhuma alteração foi feita.");
        }

        // Se a nova role for GERENTE, verifica se já existe outro gerente
        if (user.getRole() == UserRole.GERENTE) {
            boolean gerenteExiste = userRepository.existsByRole(UserRole.GERENTE);
            // Se existir outro gerente diferente do atual, lança exceção
            if (gerenteExiste && usuario.getRole() != UserRole.GERENTE) {
                throw new UnathorizedException("Já existe um gerente cadastrado.");
            }
        }

        usuario.setRole(user.getRole());
        return userRepository.save(usuario);
    }

    public Optional<UserModel> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
