package com.example.ecommerce.entities.dto;

import com.example.ecommerce.entities.CoordonneesBancaires;
import com.example.ecommerce.entities.enumerations.Gender;
import com.example.ecommerce.entities.enumerations.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UtilisateurRequestDTO {
    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;
    private CoordonneesBancaires coordonneesBancaires;
    private String adresseLivraison;
}
