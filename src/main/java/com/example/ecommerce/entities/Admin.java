package com.example.ecommerce.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@DiscriminatorValue("Admin")
public class Admin extends Utilisateur {
}
