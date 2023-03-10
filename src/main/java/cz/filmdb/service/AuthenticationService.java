package cz.filmdb.service;

import cz.filmdb.model.*;
import cz.filmdb.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user, user.getId(), user.getUserRole());

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request, UserRole role) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(role)
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user, user.getId(), user.getUserRole());

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        // TODO: Handle the exception
        User user = userRepository.findByUsername(request.getUsername());

        String jwtToken = jwtService.generateToken(user, user.getId(), user.getUserRole());

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}

