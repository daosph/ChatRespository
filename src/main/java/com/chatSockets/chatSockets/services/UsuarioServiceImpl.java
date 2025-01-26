package com.chatSockets.chatSockets.services;

import com.chatSockets.chatSockets.configuration.EncodePaswords;
import com.chatSockets.chatSockets.dto.RolDto;
import com.chatSockets.chatSockets.dto.UserRegisterDto;
import com.chatSockets.chatSockets.entity.Rol;
import com.chatSockets.chatSockets.entity.Usuario;
import com.chatSockets.chatSockets.repository.RolRepository;
import com.chatSockets.chatSockets.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = usuario.getRol() != null ? usuario.getRol().getNombre() : "USER";

        log.info("User found: {}, role: {}", username, role);

        return User
                .withUsername(usuario.getUsuario())
                .password(usuario.getPassword())
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

    @Override
    public String registerUser(UserRegisterDto usuario) {
        Usuario usuarioEntity = new Usuario();
        EncodePaswords encodePaswords = new EncodePaswords();

        String paswordCodifiing = encodePaswords.encodeUserPasword(usuario.getPassword());
        Rol rol = new Rol();
        rol.setId(usuario.getRol());

        usuarioEntity.setNombre(usuario.getName());
        usuarioEntity.setApellido(usuario.getLastName());
        usuarioEntity.setPassword(paswordCodifiing);
        usuarioEntity.setTelefono(usuario.getTelephone());
        usuarioEntity.setUsuario(usuario.getUser());
        usuarioEntity.setRol(rol);

        usuarioRepository.save(usuarioEntity);

        return "Usuario registrado exitosamente";
    }

    @Override
    public List<RolDto> getRoles() {
        return rolRepository.findAllRolDto();
    }

}
