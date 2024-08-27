package com.demo.user.management;

import com.demo.user.management.entity.Portfolio;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.repo.PortfolioRepository;
import com.demo.user.management.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class UserManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Override
	public void run(String... args) throws Exception {

		/*SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Base64 Encoded Secret Key: " + base64EncodedKey);*/

		List<User> adminUser = userRepository.findByRoles(Role.ADMIN);
		if (adminUser.isEmpty()) {
			User user = new User();
			user.setName("admin-name");
			user.setEmail("admin@gmail.com");
			user.setRoles(List.of(Role.ADMIN));
			user.setUsername("admin123");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setCreatedTime(ZonedDateTime.now());
			user.setLastModifiedTime(ZonedDateTime.now());
			user.setStatus(UserStatus.APPROVED);
			user.setLastModifiedBy(user.getUsername());
			user.setDetails("created by cmd line");
			userRepository.save(user);
		}

		Portfolio p1 = new Portfolio("LOAN", 2000.0, "GBP");
		Portfolio p2 = new Portfolio("INVESTMENT", 12000.0, "USD");
		Portfolio p3 = new Portfolio("FD", 5000.0, "GBP");
		portfolioRepository.save(p1);
		portfolioRepository.save(p2);
		portfolioRepository.save(p3);
	}
}
