package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.nuce.ducnh.config.GenerateRandomPassword;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.controllers.DTO.request.LoginRequest;
import vn.nuce.ducnh.controllers.DTO.request.SignupRequest;
import vn.nuce.ducnh.controllers.DTO.response.JwtResponse;
import vn.nuce.ducnh.controllers.DTO.response.MessageResponse;
import vn.nuce.ducnh.entity.ERole;
import vn.nuce.ducnh.entity.Role;
import vn.nuce.ducnh.entity.User;
import vn.nuce.ducnh.entity.repository.RoleRepository;
import vn.nuce.ducnh.entity.repository.UserRepository;
import vn.nuce.ducnh.security.jwt.JwtUtils;
import vn.nuce.ducnh.entity.UserDetailsImpl;
import vn.nuce.ducnh.services.AuthenticationService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws DucnhException {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) throws DucnhException {
        return authenticationService.registerUser(signUpRequest);
    }
}
