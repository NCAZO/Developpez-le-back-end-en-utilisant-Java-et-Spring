package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }  throw  new RuntimeException(email+ "does not exist");
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User saveUserInfo(User user) throws Exception {
        if (user.getId() == null) {
            throw new Exception("User do not exist !");
        }

        Optional<User> currentUserId = userRepository.findById(user.getId());

        if (currentUserId.isPresent()) {
            currentUserId.get().setEmail(user.getEmail());
            currentUserId.get().setName(user.getName());
            currentUserId.get().setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(currentUserId.get());
        } else {
            throw new Exception("User do not exist !");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // On cherche l'utilisateur par son email dans ce cas
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}