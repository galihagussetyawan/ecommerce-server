package com.server.services.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.server.domain.Image;
import com.server.domain.Product;
import com.server.repository.ImageRepository;
import com.server.repository.ProductRepository;
import com.server.services.ImageService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final Path root = Paths.get("static/images");
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public void init() {

        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Image getImageById(int id) {
        return imageRepository.findById((long) id).orElse(null);
    }

    @Override
    public Image save(MultipartFile image) {

        try {
            UUID randomString = UUID.randomUUID();

            // Path file = root.resolve(image.getOriginalFilename());
            // Resource resource = new UrlResource(file.toUri());

            Image images = Image.builder()
                    .name(randomString.toString())
                    .format(image.getContentType())
                    .url("http://localhost:8080/api/image?image=" + randomString.toString())
                    .build();

            log.info("uploaded image: " + image.getOriginalFilename());

            Files.copy(image.getInputStream(), this.root.resolve(randomString.toString()));
            return imageRepository.save(images);

        } catch (Exception e) {

            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {

        try {

            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Image not found !");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public void addImageToProduct(int imageId, int productId) {

        Image image = imageRepository.findById((long) imageId).orElseThrow(null);
        Product product = productRepository.findById((long) productId).orElseThrow(null);

        product.getImages().add(image);

        log.info("ADD_IMAGE_TO_PRODUCT: " + productRepository.save(product));

        productRepository.save(product);
    }
}