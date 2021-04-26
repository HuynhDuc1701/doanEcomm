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
import vn.nuce.ducnh.entity.repository.UserRepository;
import vn.nuce.ducnh.security.jwt.JwtUtils;

import javax.mail.MessagingException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GenerateRandomPassword generateRandomPassword;

    @Autowired
    EmailService emailService;

    public ResponseEntity<?> login(LoginRequest loginRequest) throws DucnhException {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(!userDetails.isUserActive())
                throw new DucnhException("ACCOUNT_INACTIVE", "Tài khoản đã bị vô hiệu.", HttpStatus.BAD_REQUEST);


            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail()));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) throws DucnhException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new DucnhException("USERNAME_EXIST", "Tên đăng nhập đã tồn tại.", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DucnhException("EMAIL_EXIST", "Email đã tồn tại.", HttpStatus.BAD_REQUEST);
        }


        String genPassword = generateRandomPassword.generateRandom();
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(genPassword),
                Status.ACTIVE
                );

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
