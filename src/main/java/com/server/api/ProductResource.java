package com.server.api;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.server.domain.Category;
import com.server.domain.Product;
import com.server.domain.Store;
import com.server.domain.User;
import com.server.services.CategoryService;
import com.server.services.ProductService;
import com.server.services.StoreService;
import com.server.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final UserService userService;
    private final StoreService storeService;
    private final CategoryService categoryService;

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
    @PostAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequest productRequest, Principal principal) {

        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/product/save").toUriString());

        User user = userService.getUser(principal.getName());
        Store store = storeService.getStoreByUser(user);
        Category category = categoryService.getCategoryByName(productRequest.getCategory());

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .categories(Arrays.asList(category))
                .price(productRequest.getPrice())
                .size(productRequest.getSize())
                .stock(productRequest.getStock())
                .store(store)
                .build();

        log.info("save product : {}", product);

        return ResponseEntity
                .created(uri)
                .body(productService.saveProduct(product));
    }

    @GetMapping("/product")
    // @PostAuthorize("hasAuthority('BUYER')")
    @PostAuthorize("permitAll()")
    public ResponseEntity<Optional<Product>> getProduct(@RequestParam Long id) {
        Optional<Product> responseData = productService.getProduct(id);

        try {
            if (responseData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/products/category/{id}")
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

    @GetMapping("/product/created/{username}")
    public ResponseEntity getProductBycreatedBy(@PathVariable("username") String username) {

        List<Product> products = productService.getProductBycreatedBy(username);

        return ResponseEntity.ok().body(products);
    }

    // @PostMapping("/add-category-to-product")
    // public ResponseEntity<?> addCategoryToProduct() {

    // }
}

@Data
class ProductRequest {
    private String name;
    private String description;
    private String category;
    private long price;
    private int size;
    private int stock;
}