package vn.nuce.ducnh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nuce.ducnh.controllers.DTO.DucnhException;
import vn.nuce.ducnh.entity.ERole;
import vn.nuce.ducnh.entity.Role;
import vn.nuce.ducnh.entity.repository.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	RoleRepository roleRepository;


	@GetMapping("/all")
	public String allAccess() throws DucnhException {
		throw new DucnhException("test", HttpStatus.BAD_REQUEST);
//		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@PostConstruct
	@GetMapping("/init-role")
	public String initRole(){
		Optional<Role> roleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
		if(!roleAdmin.isPresent()) roleRepository.save(new Role(ERole.ROLE_ADMIN));

		Optional<Role> roleMod = roleRepository.findByName(ERole.ROLE_MODERATOR);
		if(!roleMod.isPresent()) roleRepository.save(new Role(ERole.ROLE_MODERATOR));

		Optional<Role> roleUser = roleRepository.findByName(ERole.ROLE_USER);
		if(!roleUser.isPresent()) roleRepository.save(new Role(ERole.ROLE_USER));
		System.out.println("\ninit role");
		return "OK";
	}
}
