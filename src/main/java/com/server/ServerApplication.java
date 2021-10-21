package com.server;

import java.util.ArrayList;

import com.server.domain.Product;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.services.ProductService;
import com.server.services.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "BUYER"));
			userService.saveRole(new Role(null, "SELLER"));
			userService.saveRole(new Role(null, "ADMIN"));

			userService.saveUser(new User(null, "galih", "galihagus", "admin", new ArrayList<>()));
			userService.saveUser(new User(null, "indah", "indahsania", "admin", new ArrayList<>()));

			userService.addRoleToUser("galihagus", "SELLER");
			userService.addRoleToUser("indahsania", "BUYER");
		};
	}

	@Bean
	CommandLineRunner createDummyProductRunner(ProductService productService) {
		return args -> {
			productService.saveProduct(new Product(null, "Lenovo ideapad slim 3"));
		};
	}
}
