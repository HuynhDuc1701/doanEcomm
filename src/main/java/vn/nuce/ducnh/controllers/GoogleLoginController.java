package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.security.jwt.JwtUtils;

@RestController
public class GoogleLoginController {
    @Autowired
    JwtUtils jwtUtils;

//    TODO create user login as google account
//    google account can get jwt but cannot authenticate

    @RequestMapping
    public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) throws DucnhException {
        try {
            String jwt = jwtUtils.generateJwtTokenGoogleAccount(principal);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            throw new DucnhException("GOOGLE_LOGIN_FAILED", "Error: google login failed!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
