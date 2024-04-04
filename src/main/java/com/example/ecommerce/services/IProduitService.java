package com.example.ecommerce.services;

import com.example.ecommerce.entities.Produit;
import com.example.ecommerce.entities.dto.ProduitDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IProduitService {
    Produit addProduct(Long vendeurId, Long categoryId, ProduitDTO produit);
}
