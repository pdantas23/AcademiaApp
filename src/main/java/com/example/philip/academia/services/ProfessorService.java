package com.example.philip.academia.services;

import com.example.philip.academia.dtos.professores.ProfessorDTO;
import com.example.philip.academia.enums.UserRole;
import com.example.philip.academia.enums.professores.Especializacao;
import com.example.philip.academia.exceptionhandler.exceptions.DuplicateResourceException;
import com.example.philip.academia.exceptionhandler.exceptions.EventNotFoundException;
import com.example.philip.academia.models.ProfessorModel;
import com.example.philip.academia.models.UserModel;
import com.example.philip.academia.repositories.ProfessorRepository;
import com.example.philip.academia.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfessorDTO buscarPorId(Long id) {
        ProfessorModel professor = professorRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Professor não encontrado com id " + id));

        return new ProfessorDTO(
                professor.getId(),
                professor.getUser().getUsername(),
                null,
                professor.getUser().getEmail(),
                professor.getUser().getCpf(),
                professor.getEspecialidade(),
                professor.getFormacao()
        );
    }

    @Transactional
    public ProfessorDTO atualizarPorId(Long id, ProfessorDTO professorDTO) {
        ProfessorModel professor = professorRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Professor não encontrado com id " + id));

        professor.setEspecialidade(professorDTO.getEspecialidade());
        professor.setFormacao(professorDTO.getFormacao());

        UserModel user = professor.getUser();
        user.setUsername(professorDTO.getUsername());
        user.setEmail(professorDTO.getEmail());
        user.setCpf(professorDTO.getCpf());
        userRepository.save(user);

        ProfessorModel atualizado = professorRepository.save(professor);

        return new ProfessorDTO(
                atualizado.getId(),
                atualizado.getUser().getUsername(),
                null,
                atualizado.getUser().getEmail(),
                atualizado.getUser().getCpf(),
                atualizado.getEspecialidade(),
                atualizado.getFormacao()
        );
    }

    public List<ProfessorDTO> listarTodos() {
        List<ProfessorModel> professores = professorRepository.findAll();

        if (professores.isEmpty()) {
            throw new EventNotFoundException(
                    "Nenhum professor cadastrado."
            );
        }

        return professorRepository.findAll()
                .stream()
                .map(professor -> new ProfessorDTO(
                        professor.getId(),
                        professor.getUser().getUsername(),
                        null,
                        professor.getUser().getEmail(),
                        null,
                        professor.getEspecialidade(),
                        professor.getFormacao()
                ))
                .collect(Collectors.toList());
    }

    public void deletarPorId(Long id) {
        ProfessorModel professor = professorRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Professor não encontrado com id " + id));

        professorRepository.delete(professor);
    }

    public List<ProfessorDTO> listarPorEspecialidade(Especializacao especialidade) {
        List<ProfessorModel> professores = professorRepository.findByEspecialidade(especialidade);

        if (professores.isEmpty()) {
            throw new EventNotFoundException(
                    "Nenhum profissional com especialidade " + especialidade + " cadastrado."
            );
        }

        return professores.stream()
                .map(prof -> new ProfessorDTO(
                        prof.getId(),
                        prof.getUser().getUsername(),
                        null,
                        prof.getUser().getEmail(),
                        null,
                        prof.getEspecialidade(),
                        prof.getFormacao()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void cadastrarProfessor(ProfessorDTO professorDTO) {
        userRepository.findByEmail(professorDTO.getEmail())
                .ifPresent(u -> { throw new DuplicateResourceException("Email já cadastrado"); });

        userRepository.findByCpf(professorDTO.getCpf())
                .ifPresent(u -> { throw new DuplicateResourceException("CPF já cadastrado"); });

        UserModel user = new UserModel(
                null,
                professorDTO.getUsername(),
                passwordEncoder.encode(professorDTO.getPassword()),
                professorDTO.getEmail(),
                professorDTO.getCpf(),
                UserRole.PROFESSOR
        );
        userRepository.save(user);

        ProfessorModel professor = new ProfessorModel(
                null,
                user,
                professorDTO.getEspecialidade(),
                professorDTO.getFormacao(),
                new ArrayList<>()
        );
        professorRepository.save(professor);

    }
}
