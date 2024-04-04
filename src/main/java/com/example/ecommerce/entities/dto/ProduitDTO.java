package com.example.ecommerce.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDTO {
    private String name;
    private String description;
    private double price;
    private String reference;
    private LocalDateTime createdAt;
    private int quantite;
    private String image;



    public ProduitDTO(String name, String description, double price, String reference, int quantite, String originalFileName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.reference = reference;
        this.quantite = quantite;
        this.image = originalFileName;
    }
}
