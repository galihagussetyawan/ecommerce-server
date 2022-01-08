package com.server.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.server.domain.Category;
import com.server.domain.Product;
import com.server.services.ProductService;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ProductResource {

    private final ProductService productService;

    @GetMapping("/products")
    @PostAuthorize("permitAll()")
    public ResponseEntity<List<Product>> getProducts() {
        log.info("GET ALL PRODUCTS");

        List<Product> response = productService.getProducts();

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("/product/save")
    @PostAuthorize("permitAll()")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequest productRequest) {

        Product product = new Product(null, productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), productRequest.getSize(), productRequest.getStock(), new ArrayList<>());

        log.info("save product : {}", product);

        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/product/save").toUriString());

        return ResponseEntity
                .created(uri)
                .body(productService.saveProduct(product));
    }

    @GetMapping("/product/{id}")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable("id") Long id) {
        Optional<Product> responseData = productService.getProduct(id);

        try {
            if (responseData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/category/{id}")
    @PostAuthorize("permitAll()")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("id") Category category) {
        log.info("get product by category: {}", category.getName());

        List<Product> products = productService.getProductByCategory(category);
        // List<Product> response = products
        // .stream()
        // .collect(Collectors.toList());

        log.info("response product by category : {}", products);

        return ResponseEntity
                .ok()
                .body(products);
    }
}

@Data
class ProductRequest {
    private String name;
    private String description;
    private long price;
    private int size;
    private int stock;
}