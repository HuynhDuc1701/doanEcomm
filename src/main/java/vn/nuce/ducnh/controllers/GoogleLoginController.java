package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.controllers.DTO.response.JwtResponse;
import vn.nuce.ducnh.entity.Status;
import vn.nuce.ducnh.entity.User;
import vn.nuce.ducnh.entity.repository.UserRepository;
import vn.nuce.ducnh.security.jwt.JwtUtils;

import java.util.Optional;

@RestController
public class GoogleLoginController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;



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
                        Status.ACTIVE);
                userRepository.save(user);
            }
            Optional<User> user = userRepository.findByEmail(gEmail);
            String jwt = jwtUtils.generateJwtTokenGoogleAccount(principal);
            return ResponseEntity.ok(new JwtResponse(jwt,user.get().getId(),gEmail,gEmail));
        } catch (Exception e) {
            throw new DucnhException("GOOGLE_LOGIN_FAILED", "lỗi login với tài khoản google của bạn.!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
