package com.example.philip.academia.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("[DEBUG] Authorization header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("[DEBUG] Token extraído: " + token);

            try {
                String email = tokenService.validateToken(token);
                System.out.println("[DEBUG] Email do token: " + email);

                if (email != null && !email.isEmpty() &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    System.out.println("[DEBUG] UserDetails carregado: " + userDetails.getUsername());
                    System.out.println("[DEBUG] Authorities: " + userDetails.getAuthorities());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("[DEBUG] SecurityContext atualizado com usuário autenticado");
                } else {
                    System.out.println("[DEBUG] Token inválido ou usuário já autenticado no contexto");
                }

            } catch (Exception e) {
                System.out.println("[DEBUG] Erro ao validar token: " + e.getMessage());
            }
        } else {
            System.out.println("[DEBUG] Header Authorization ausente ou não contém Bearer");
        }

        filterChain.doFilter(request, response);
    }

}
