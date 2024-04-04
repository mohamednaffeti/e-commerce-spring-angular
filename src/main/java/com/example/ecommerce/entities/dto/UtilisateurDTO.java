package com.example.ecommerce.entities.dto;

import com.example.ecommerce.entities.CoordonneesBancaires;
import com.example.ecommerce.entities.enumerations.Gender;
import com.example.ecommerce.entities.enumerations.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilisateurDTO {
    Long id;
    String userName;
   // String password;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    LocalDate creationDate;
    String adresseLivraison;
    Gender gender;
    CoordonneesBancaires coordonneesBancaires;
    List<GrantedAuthority> authorities;
}
