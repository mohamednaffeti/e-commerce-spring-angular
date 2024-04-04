package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.dto.ProduitDTO;
import com.example.ecommerce.services.IProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private IProduitService produitService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";

    @PostMapping(value = "/{vendeurId}/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Produit> addProduct(@PathVariable Long vendeurId, @PathVariable Long categoryId, @RequestParam("name") String name,
                                              @RequestParam("description") String description, @RequestParam("price") double price,
                                              @RequestParam("reference") String reference, @RequestParam("quantite") int quantite,
                                               @RequestParam("image") MultipartFile image) throws IOException {

        String originalFileName = image.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, originalFileName);
        Files.write(fileNameAndPath, image.getBytes());

        ProduitDTO produitDTO = new ProduitDTO(name, description, price, reference,  quantite, originalFileName);

        Produit createdProduit = produitService.addProduct(vendeurId, categoryId, produitDTO);
        if (createdProduit != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduit);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}