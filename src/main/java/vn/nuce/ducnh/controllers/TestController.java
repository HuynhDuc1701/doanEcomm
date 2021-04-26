package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.entity.Status;
import vn.nuce.ducnh.entity.User;
import vn.nuce.ducnh.entity.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/all")
    public String allAccess() throws DucnhException {
        throw new DucnhException("CODE","tiếng việt", HttpStatus.BAD_REQUEST);
//		return "Public Content.";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Board.";
    }


    @PostConstruct
    public void initUser() {
        Optional<User> admin = userRepository.findByUsername("admin");
        if(!admin.isPresent()){
            userRepository.save(new User("admin", "admin@gmail.com", passwordEncoder.encode("admin"),Status.ACTIVE));
        }else {
            admin.get().setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin.get());
        }
        System.out.println("\ninit user admin");
    }
}
