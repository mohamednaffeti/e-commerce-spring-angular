package com.example.ecommerce.services;

import com.example.ecommerce.Repositories.CategorieRepository;
import com.example.ecommerce.entities.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie getCategoryById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    @Override
    public Categorie createCategory(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie updateCategory(Long id, Categorie categorie) {
        if (categorieRepository.existsById(id)) {
            categorie.setIdCategory(id);
            return categorieRepository.save(categorie);
        }
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categorieRepository.deleteById(id);
    }
}