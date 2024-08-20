package com.chatSockets.chatSockets.controller;

import com.chatSockets.chatSockets.dto.UserResponse;
import com.chatSockets.chatSockets.entity.Usuario;
import com.chatSockets.chatSockets.services.AuthService;
import com.chatSockets.chatSockets.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthService authService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String password) {
        log.info("Attempting to login with username: {}", usuario);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Login successful for username: {}", usuario);

            // Retrieve user details
            Usuario user = usuarioService.findByUsername(usuario);
            if (user != null) {
                // Create response object
                UserResponse response = new UserResponse(user.getId(), user.getRol().getNombre());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
        } catch (AuthenticationException e) {
            log.error("Authentication failed for username: {}. Exception: {}", usuario, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        log.info("User logged out");
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/prueba")
    public ResponseEntity<String> prueba() {
        log.info("Prueba endpoint accessed");
        return ResponseEntity.ok("Hola");
    }
}
