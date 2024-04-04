package com.example.ecommerce.Repositories;

import com.example.ecommerce.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByProduitIdProduit(Long produitId);

}
