package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dto.requests.LoginRequestDto;
import com.openclassrooms.chatop.dto.requests.RegisterRequestDto;
import com.openclassrooms.chatop.dto.responses.AuthResponseDto;
import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.services.AuthService;
import com.openclassrooms.chatop.services.JwtService;
import com.openclassrooms.chatop.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        String token = authService.register(registerRequestDto.getEmail(), registerRequestDto.getName(), registerRequestDto.getPassword());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

//	@PostMapping("/logOut")
//	public ResponseEntity<?> logoutUser() {
//		ResponseCookie cookie = jwtService.getCleanJwtCookie();
//		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//				.body(new MessageResponse("You've been signed out!"));
//	}

//    @GetMapping("/me")
//    public ResponseEntity<User> getMe(HttpServletRequest request) throws Exception {
//        String authHeader = request.getHeader("Authorization");
//
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            throw new Exception("Exception message");
//        }
//
//        String token = authHeader.substring(7);
//
//        // Extraire le username du token
//        String username = jwtService.getUserNameFromJwtToken(token);
//        return ResponseEntity.ok(userService.getUserByEmail(username));
//    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe(HttpServletRequest request) throws Exception {
        return authService.getMe(request);
    }
}