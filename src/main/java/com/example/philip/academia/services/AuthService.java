package com.example.philip.academia.services;

import com.example.philip.academia.dtos.users.LoginDTO;
import com.example.philip.academia.dtos.users.UserDTO;
import com.example.philip.academia.security.CustomUserDetails;
import com.example.philip.academia.security.CustomUserDetailsService;
import com.example.philip.academia.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    public String logarUsuario(LoginDTO loginDTO){
        //Autentica email e senha.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        //Recupera detalhes do usuário autenticado.
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        //Gera o token JWT.
        return tokenService.generateToken(userDetails);
    }
}
