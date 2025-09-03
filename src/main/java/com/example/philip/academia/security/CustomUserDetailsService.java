package com.example.philip.academia.security;

import com.example.philip.academia.models.UserModel;
import com.example.philip.academia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuario pelo e-mail
        UserModel usuario = userService.buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Retorna um objeto CustomUserDetails que o Spring Security entende
        return new CustomUserDetails(usuario);
    }
}
