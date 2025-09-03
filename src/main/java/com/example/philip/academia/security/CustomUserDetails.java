package com.example.philip.academia.security;

import com.example.philip.academia.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserModel usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
    }

    public String getEmail() {
        return usuario.getEmail();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword(); // senha já criptografada
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // login será feito via email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // sempre válido, pode customizar se quiser expiração de conta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // sempre desbloqueado, pode customizar
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // senha não expira, pode customizar
    }

    @Override
    public boolean isEnabled() {
        return true; // sempre habilitado, pode customizar
    }
}

