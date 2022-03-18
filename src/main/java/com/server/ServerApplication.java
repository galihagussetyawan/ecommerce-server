package com.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import com.server.auditable.AuditorAwareImpl;
import com.server.domain.Category;
import com.server.domain.Product;
import com.server.domain.Role;
import com.server.domain.Store;
import com.server.domain.User;
import com.server.domain.DetailContact;
import com.server.services.CartService;
import com.server.services.CategoryService;
import com.server.services.ImageService;
import com.server.services.ProductService;
import com.server.services.StoreService;
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
			userService.saveRole(new Role(null, "ADMIN"));
			userService.saveRole(new Role(null, "SELLER"));

			User galih = User.builder()
					.name("galih")
					.username("galihagus")
					.password("admin")
					.build();

			User indah = User.builder()
					.name("indah")
					.username("indahsania")
					.password("admin")
					.build();

			User eva = User.builder()
					.name("evah")
					.username("evadwi")
					.password("admin")
					.build();

			// userService.saveUser(new User(null, "galih", "galihagus", "admin", new
			// ArrayList<>()));
			// userService.saveUser(new User(null, "indah", "indahsania", "admin", new
			// ArrayList<>()));
			// userService.saveUser(new User(null, "eva", "evadwi", "admin", new
			// ArrayList<>()));

			userService.saveUser(galih);
			userService.saveUser(indah);
			userService.saveUser(eva);

			userService.addRoleToUser("galihagus", "BUYER");
			userService.addRoleToUser("indahsania", "BUYER");
			userService.addRoleToUser("evadwi", "BUYER");

			// // create data user detail
			User user = userService.getUser("galihagus");
			String birthDate = "13/08/1999";
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);

			DetailContact userDetail = DetailContact.builder()
					.firstname("galih")
					.lastname("setyawan")
					.birth(date)
					.address1("Jatiprahu Karangan Trenggalek")
					.city("trenggalek")
					.state("Jawa Timur")
					.phone("085856215653")
					.email("setyawan.galih11@gmail.com")
					.build();

			userService.addUserDetail(user, userDetail);
		};
	}

	// @Bean
	CommandLineRunner init(ProductService productService) {

		Product product1 = Product.builder()
				.name("Lenovo Ideapad Slim 5")
				.description("desc")
				.price(5000)
				.size(8)
				.stock(10)
				.build();

		Product product2 = Product.builder()
				.name("Manjaro Gnome")
				.description("desc")
				.price(5000)
				.size(8)
				.stock(10)
				.build();

		Product product3 = Product.builder()
				.name("Masker medical anti corona")
				.description("desc")
				.price(5000)
				.size(8)
				.stock(6)
				.build();

		return args -> {

			productService.saveProduct(product1);
			productService.saveProduct(product2);
			productService.saveProduct(product3);
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

	// @Bean
	// CommandLineRunner printTest(ProductService productService) {
	// return args -> {
	// productService.addCategoryToProduct("Lenovo Ideapad Slim 5", "Elektronik");
	// };
	// }

	// @Bean
	// CommandLineRunner testCart(CartService cartService) {

	// return args -> {
	// cartService.addToCart(1, 1, 5);
	// cartService.addToCart(1, 2, 10);
	// cartService.addToCart(1, 3, 2);
	// };
	// }

	// @Bean
	CommandLineRunner createStore(StoreService storeService, UserService userService) {

		return args -> {

			User user = userService.getUser("galihagus");
			Store store = Store.builder()
					.name("Alienx Store")
					.username("alienxstore")
					.user(user)
					.build();

			storeService.createStore(user, store);

		};
	}
}