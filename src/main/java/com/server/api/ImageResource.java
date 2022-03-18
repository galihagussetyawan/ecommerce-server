package com.server.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.server.domain.Image;
import com.server.services.ImageService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ImageResource {

    private final ImageService imageService;

    @PostMapping("/product/images")
    public ResponseEntity<?> uploadProductImages(@RequestParam("images") MultipartFile[] images) {
        String message = "";

        try {
            List<Image> listImage = new ArrayList<>();

            Arrays.asList(images).stream().forEach(image -> {
                listImage.add(imageService.save(image));
            });

            return ResponseEntity.status(HttpStatus.CREATED).body(listImage);

        } catch (Exception e) {
            message = "Could not upload the file !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam("image") String imageName) {

        try {
            Resource resource = imageService.load(imageName);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/image-to-product")
    public ResponseEntity<?> addImageToProduct(@RequestBody ImageToProductRequest requestBody) {

        imageService.addImageToProduct(requestBody.getImage(), requestBody.getProduct());

        return ResponseEntity.ok().body("success");
    }
}

@Data
class ImageToProductRequest {
    private int image;
    private int product;
}