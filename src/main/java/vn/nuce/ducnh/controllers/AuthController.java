package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.controllers.DTO.request.LoginRequest;
import vn.nuce.ducnh.controllers.DTO.request.SignupRequest;
import vn.nuce.ducnh.services.AuthenticationService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

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

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @RequestMapping("index")
    public String in() {
        return "index.html";
    }
}
