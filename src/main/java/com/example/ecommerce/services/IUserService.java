package com.example.ecommerce.services;

import com.example.ecommerce.entities.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService {
    List<Utilisateur> getAllUsers();
    Utilisateur createUser(Utilisateur user);
    Utilisateur createAdmin(Utilisateur admin);
    void deleteUser(Long id);
    Utilisateur findById(Long id);
    UserDetails loadUserByUsername(String username);
}
