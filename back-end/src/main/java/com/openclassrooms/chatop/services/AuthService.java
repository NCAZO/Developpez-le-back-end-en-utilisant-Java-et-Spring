package com.openclassrooms.chatop.services;


import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserService userService,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

//    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
//        if (userService.existsByEmail(registerRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already taken!"));
//        }
//
//        Long time = Date.from(Instant.now()).getTime();
//
//        // Create new user's account
//        User newUser = new User();
//        newUser.setId(null);
//        newUser.setName(registerRequest.getName());
//        newUser.setEmail(registerRequest.getEmail());
//        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//
//        newUser.setCreated_at(new Date(time));
//        newUser.setUpdated_at(new Date());
//
//        userService.createUser(newUser);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }

    public String register(String email, String name, String password) {
        if (userService.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exist !");
        }

        Long time = Date.from(Instant.now()).getTime();

        // Create new user's account
        User newUser = new User();
        newUser.setId(null);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));

        newUser.setCreated_at(new Date(time));

        userService.createUser(newUser);

        return jwtService.generateToken(email);
    }

    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        return jwtService.generateToken(email);
    }

    public ResponseEntity<User> getUserById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> getMe(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new Exception("Exception message");
        }

        String token = authHeader.substring(7);

        // Extraire le username du token
        String username = jwtService.getUserNameFromJwtToken(token);
        return ResponseEntity.ok(userService.getUserByEmail(username));
    }
}