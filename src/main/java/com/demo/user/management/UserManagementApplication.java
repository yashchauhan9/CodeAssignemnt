package com.demo.user.management;

import com.demo.user.management.entity.Entitlement;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.repo.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Optional;

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}


	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Base64 Encoded Secret Key: " + base64EncodedKey);*/

		Optional<User> adminUser = userRepository.findByRole(Role.ADMIN);
		if (adminUser.isEmpty()) {
			User user = new User();
			user.setName("admin-name");
			user.setEmail("admin@gmail.com");
			user.setRole(Role.ADMIN);
			user.setUsername("admin123");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setCreatedTime(ZonedDateTime.now());
			user.setLastModifiedTime(ZonedDateTime.now());
			user.setStatus(UserStatus.APPROVED);
			user.setLastModifiedBy(user.getUsername());
			user.setDetails("created by cmd line");
			userRepository.save(user);
		}

	}
}
