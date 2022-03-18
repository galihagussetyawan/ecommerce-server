package com.server.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.server.domain.Image;
import com.server.domain.Product;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public void init();

    public Image save(MultipartFile image);

    public Image getImageById(int id);

    public Resource load(String filename);

    public void addImageToProduct(int imageId, int productId);

    public void deleteAll();

    public Stream<Path> loadAll();
}