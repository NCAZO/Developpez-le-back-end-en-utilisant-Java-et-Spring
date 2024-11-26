package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.DTO.Requests.LoginRequest;
import com.openclassrooms.chatop.DTO.Requests.RegisterRequest;
import com.openclassrooms.chatop.DTO.Responses.AuthResponse;
import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.services.AuthService;
import com.openclassrooms.chatop.services.JwtService;
import com.openclassrooms.chatop.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

//	@PostMapping("/logOut")
//	public ResponseEntity<?> logoutUser() {
//		ResponseCookie cookie = jwtService.getCleanJwtCookie();
//		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//				.body(new MessageResponse("You've been signed out!"));
//	}

    @GetMapping("/me")
    public User getMe() {
        User newUSer = authService.getMe();
        newUSer.setPassword(null);
        return newUSer;
    }
}