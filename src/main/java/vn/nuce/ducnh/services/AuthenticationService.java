package vn.nuce.ducnh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.nuce.ducnh.config.GenerateRandomPassword;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.controllers.DTO.request.LoginRequest;
import vn.nuce.ducnh.controllers.DTO.request.SignupRequest;
import vn.nuce.ducnh.controllers.DTO.response.JwtResponse;
import vn.nuce.ducnh.controllers.DTO.response.MessageResponse;
import vn.nuce.ducnh.entity.*;
import vn.nuce.ducnh.entity.repository.RoleRepository;
import vn.nuce.ducnh.entity.repository.UserRepository;
import vn.nuce.ducnh.security.jwt.JwtUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GenerateRandomPassword generateRandomPassword;

    @Autowired
    EmailService emailService;

    public ResponseEntity<?> login(LoginRequest loginRequest) throws DucnhException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());


            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } catch (Exception e) {
            throw new DucnhException("USERNAME_OR_PASSWORD_INCORRECT", "Error: Username or password incorrect!",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws DucnhException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new DucnhException("USERNAME_EXIST", "Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DucnhException("EMAIL_EXIST", "Error: Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches("^(BUYER|SELLER)$", signUpRequest.getUserType().toString())) {
            throw new DucnhException("USER_TYPE_ERROR", "Error: User type must be SELLER or BUYER!", HttpStatus.BAD_REQUEST);
        }

        String genPassword = generateRandomPassword.generateRandom();
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(genPassword),
                EUserType.valueOf(signUpRequest.getUserType()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        try {
            emailService.sendOtpMessage(user.getEmail(), "Your password",
                    "your password is : " + genPassword);
            System.out.println("send password to email : " + genPassword);
        } catch (MessagingException me) {
            System.out.println("\nerror to send password to email : " + genPassword);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
