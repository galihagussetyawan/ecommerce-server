package com.server.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.server.domain.Product;
import com.server.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ProductResource.class })
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    Product product1 = new Product(1L, "Manjaro Gnome 6", "jancokk", 1000000, 10, 5, new ArrayList<>());
    Product product2 = new Product(2L, "Manjaro Gnome 5", "jancokk", 1000000, 10, 5, new ArrayList<>());
    Product product3 = new Product(3L, "Manjaro Gnome 5", "jancokk", 1000000, 10, 5, new ArrayList<>());

    List<Product> products = new ArrayList<>(Arrays.asList(product1, product2,
            product3));

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void testGetProduct() throws Exception {
        Mockito.when(productService.getProduct(1L)).thenReturn(Optional.ofNullable(product1));

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(productService.getProduct(1L))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetProducts() throws Exception {
        Mockito.when(productService.getProducts()).thenReturn(products);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(productService.getProducts())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetProductsByCategory() throws Exception {

    }
}