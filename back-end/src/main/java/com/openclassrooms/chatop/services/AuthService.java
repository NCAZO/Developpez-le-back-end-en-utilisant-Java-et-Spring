package com.openclassrooms.chatop.services;


import com.openclassrooms.chatop.DTO.Requests.LoginRequest;
import com.openclassrooms.chatop.DTO.Requests.RegisterRequest;
import com.openclassrooms.chatop.DTO.Responses.AuthResponse;
import com.openclassrooms.chatop.DTO.Responses.MessageResponse;
import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    //    private final JwtUtils jwtUtils;
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

    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        Long time = Date.from(Instant.now()).getTime();

        // Create new user's account
        User newUser = new User();
        newUser.setId(null);
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        newUser.setCreated_at(new Date(time));

        userService.createUser(newUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok().body(new AuthResponse(token, "200", "Token generated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "400", "Unknown user"));
        }


    }

    public User getUserById(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userRepository.findById(id).get();
    }

    public User getMe() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        String username = authentication.getName();

        Optional<User> _user = userService.findUserByName(username);

        //_user.get().setPassword(null);

        return _user.get();
    }

}