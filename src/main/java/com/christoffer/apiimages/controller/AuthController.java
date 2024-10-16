package com.christoffer.apiimages.controller;

import com.christoffer.apiimages.controller.dto.LoginRequest;
import com.christoffer.apiimages.controller.dto.RegistrationRequest;
import com.christoffer.apiimages.model.Role;
import com.christoffer.apiimages.model.User;
import com.christoffer.apiimages.repository.RoleRepository;
import com.christoffer.apiimages.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationRequest request) {
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getEmail()
        );

        Role defaultRole = roleRepository.findByName("USER");
        if (defaultRole == null) {
            defaultRole = new Role("USER");
            defaultRole = roleRepository.save(defaultRole);
        }

        user.setRoles(Collections.singleton(defaultRole));

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso!");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Credenciais inválidas!"));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Obtém o nome de usuário do usuário autenticado
            User user = userRepository.findByUsername(username);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build(); // Não autorizado
        }
    }
}