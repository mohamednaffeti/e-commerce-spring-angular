package com.example.ecommerce.entities.dto;

import com.example.ecommerce.entities.*;
import com.example.ecommerce.entities.enumerations.Gender;
import com.example.ecommerce.entities.enumerations.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UtilisateurResponseDTO {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private CoordonneesBancaires coordonneesBancaires;
    private String adresseLivraison;

    public UtilisateurResponseDTO(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.userName = utilisateur.getUserName();
        this.firstName = utilisateur.getFirstName();
        this.lastName = utilisateur.getLastName();
        this.email = utilisateur.getEmail();
        this.phoneNumber = utilisateur.getPhoneNumber();
        this.role = determineRole(utilisateur);
        this.creationDate = utilisateur.getCreationDate();
        this.gender = utilisateur.getGender();
        if (utilisateur instanceof Vendeur) {
            this.adresseLivraison=((Vendeur) utilisateur).getAdresseLivraison();
            this.coordonneesBancaires = ((Vendeur) utilisateur).getCoordonneesBancaires();
        }else if (utilisateur instanceof Client){
            this.adresseLivraison = ((Client) utilisateur).getAdresseLivraison();
        }
    }

    private Role determineRole(Utilisateur utilisateur) {
        if (utilisateur instanceof Client) {
            return Role.Client;
        } else if (utilisateur instanceof Vendeur) {
            return Role.Vendeur;
        } else if(utilisateur instanceof Admin){
            return Role.Admin;
        }else{
            throw new RuntimeException("No Role ");
        }
    }
}