package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.controllers.DTO.response.JwtResponse;
import vn.nuce.ducnh.entity.ERole;
import vn.nuce.ducnh.entity.EUserType;
import vn.nuce.ducnh.entity.Role;
import vn.nuce.ducnh.entity.User;
import vn.nuce.ducnh.entity.repository.RoleRepository;
import vn.nuce.ducnh.entity.repository.UserRepository;
import vn.nuce.ducnh.security.jwt.JwtUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class GoogleLoginController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;


    @Autowired
    RoleRepository roleRepository;

//    TODO create user login as google account
//    google account can get jwt but cannot authenticate
//     oauth2/authorization/google --endpoint to google login
    @RequestMapping
    public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) throws DucnhException {
        try {
            String gEmail = principal.getAttribute("email");
            if(!userRepository.existsByEmail(gEmail)) {
                User user = new User(gEmail,
                        gEmail,
                        "123456",
                        EUserType.BUYER);

                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
                user.setRoles(roles);
                userRepository.save(user);
            }
            Optional<User> user = userRepository.findByEmail(gEmail);
            String jwt = jwtUtils.generateJwtTokenGoogleAccount(principal);
            return ResponseEntity.ok(new JwtResponse(jwt,user.get().getId(),gEmail,gEmail,null));
        } catch (Exception e) {
            throw new DucnhException("GOOGLE_LOGIN_FAILED", "Error: google login failed!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
