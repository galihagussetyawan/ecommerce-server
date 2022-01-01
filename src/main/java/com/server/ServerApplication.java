package com.server;

import java.util.ArrayList;

import com.server.auditable.AuditorAwareImpl;
import com.server.domain.Cart;
import com.server.domain.Category;
import com.server.domain.Product;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.services.CartService;
import com.server.services.CategoryService;
import com.server.services.ProductService;
import com.server.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public AuditorAware auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(value = "dsdssd")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "BUYER"));
			userService.saveRole(new Role(null, "SELLER"));
			userService.saveRole(new Role(null, "ADMIN"));

			userService.saveUser(new User(null, "galih", "galihagus", "admin", new ArrayList<>()));
			userService.saveUser(new User(null, "indah", "indahsania", "admin", new ArrayList<>()));
			userService.saveUser(new User(null, "eva", "evadwi", "admin", new ArrayList<>()));

			userService.addRoleToUser("galihagus", "SELLER");
			userService.addRoleToUser("indahsania", "BUYER");
			userService.addRoleToUser("evadwi", "BUYER");
		};
	}

	@Bean
	CommandLineRunner init(ProductService productService) {
		return args -> {
			productService
					.saveProduct(new Product(null, "Lenovo Ideapad Slim 5", "desc", 5000, 8, 10, new ArrayList<>()));

			productService
					.saveProduct(new Product(null, "Manjaro Gnome", "desc", 5000, 8, 10, new ArrayList<>()));
		};
	}

	@Bean
	CommandLineRunner initCategory(CategoryService categoryService) {
		return args -> {

			categoryService.saveCategory(new Category(null, "Fashion Pria", "dummy"));
			categoryService.saveCategory(new Category(null, "Fashion Wanita", "dummy"));
			categoryService.saveCategory(new Category(null, "Anak-Anak", "dummy"));
			categoryService.saveCategory(new Category(null, "Travel & Entertaiment",
					"dummy"));
			categoryService.saveCategory(new Category(null, "Keuangan", "dummy"));
			categoryService.saveCategory(new Category(null, "Rumah Tangga", "dummy"));
			categoryService.saveCategory(new Category(null, "Supermarket", "dummy"));
			categoryService.saveCategory(new Category(null, "Kosmetik", "dummy"));
			categoryService.saveCategory(new Category(null, "Tas Sepatu", "dummy"));
			categoryService.saveCategory(new Category(null, "Alat & Aksesoris",
					"dummy"));
			categoryService.saveCategory(new Category(null, "Olahraga & Outdoor",
					"dummy"));
			categoryService.saveCategory(new Category(null, "Elektronik", "dummy"));
			categoryService.saveCategory(new Category(null, "Komputer & Laptop",
					"dummy"));
		};
	}

	@Bean
	CommandLineRunner printTest(ProductService productService) {
		return args -> {
			productService.addCategoryToProduct("Lenovo Ideapad Slim 5", "Elektronik");
		};
	}

	@Bean
	CommandLineRunner testCart(CartService cartService) {

		return args -> {
			cartService.addToCart(1, 1, 5);
			cartService.addToCart(1, 2, 10);

			// cartService.getCartByUser(1);
		};
	}
}