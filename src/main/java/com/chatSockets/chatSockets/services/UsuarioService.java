package com.chatSockets.chatSockets.services;

import com.chatSockets.chatSockets.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioService {

    UserDetails loadUserByUsername(String username);

    Optional<Usuario> login(String usuario, String password);

    Usuario findByUsername(String username);
}
