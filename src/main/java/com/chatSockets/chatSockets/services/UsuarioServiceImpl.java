package com.chatSockets.chatSockets.services;

import com.chatSockets.chatSockets.entity.Usuario;
import com.chatSockets.chatSockets.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = usuario.getRol() != null ? usuario.getRol().getNombre() : "USER";

        log.info("User found: {}, role: {}", username, role);

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsuario())
                .password(usuario.getPassword()) // La contraseña ya debería estar codificada
                .roles(role)
                .build();
    }

    @Override
    public Optional<Usuario> login(String usuario, String password) {
        log.info("Attempting login for username: {}", usuario);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(usuario);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            boolean passwordMatch = passwordEncoder.matches(password, u.getPassword());
            log.info("Password match for username: {}: {}", usuario, passwordMatch);
            if (passwordMatch) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
