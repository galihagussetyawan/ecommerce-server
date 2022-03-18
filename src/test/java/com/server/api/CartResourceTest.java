// package com.server.api;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectWriter;
// import com.server.domain.Cart;
// import com.server.domain.Product;
// import com.server.domain.User;
// import com.server.domain.DetailContact;
// import com.server.services.CartService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultMatcher;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import static
// org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest
// @AutoConfigureMockMvc
// @ContextConfiguration(classes = CartResource.class)
// public class CartResourceTest {

// @Autowired
// private MockMvc mockMvc;

// @Autowired
// private WebApplicationContext webApplicationContext;

// @MockBean
// private CartService cartService;

// ObjectWriter objectWriter = new
// ObjectMapper().writer().withDefaultPrettyPrinter();

// Product product = new Product(1L, "Lenovo Gnome 6", "description", 5000, 10,
// 10, new ArrayList<>());

// User user = User.builder()
// .id(1L)
// .name("galih agus")
// .username("galihagus")
// .password("admin")
// .build();

// // User user = new User(1L, "galih agus", "galihagus", "admin", new
// // ArrayList<>());

// @BeforeEach
// void setup() throws Exception {
// mockMvc =
// MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
// }

// @Test
// void testGetCartByUser() throws Exception {
// Cart cart = Cart.builder()
// .user(user)
// .product(product)
// .quantity(2)
// .amount(product.getPrice() * 2)
// .build();

// List<Cart> carts = new ArrayList<>(Arrays.asList(cart));

// Mockito.when(cartService.getCartByUser(1)).thenReturn(carts);

// String json = objectWriter.writeValueAsString(cartService.getCartByUser(1));
// System.out.println(json);

// this.mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/1")
// .contentType(MediaType.APPLICATION_JSON)
// .content(String.valueOf(cartService.getCartByUser(1))))
// .andExpect((ResultMatcher) status().isOk())
// .andDo(print());
// }

// @Test
// void testAddToCart() throws Exception {
// Cart cart = Cart.builder()
// .user(user)
// .product(product)
// .quantity(2)
// .amount(product.getPrice() * 10)
// .build();

// Mockito.when(cartService.addToCart(1, 1, 10)).thenReturn(cart);

// this.mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/save")
// .contentType(MediaType.APPLICATION_JSON)
// .content(String.valueOf(cartService.addToCart(1, 1, 10))))
// .andExpect(status().isCreated())
// .andDo(print());

// }
// }