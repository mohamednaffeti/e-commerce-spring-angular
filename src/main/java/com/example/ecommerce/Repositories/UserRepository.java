package com.example.ecommerce.Repositories;

import com.example.ecommerce.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByUserName(String username);
}
