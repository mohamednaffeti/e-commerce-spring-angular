package com.example.ecommerce.services;

import com.example.ecommerce.entities.Categorie;

import java.util.List;

public interface CategorieService {
    List<Categorie> getAllCategories();
    Categorie getCategoryById(Long id);
    Categorie createCategory(Categorie categorie);
    Categorie updateCategory(Long id, Categorie categorie);
    void deleteCategory(Long id);
}
