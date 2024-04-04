package com.example.ecommerce.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@DiscriminatorValue("Vendeur")
public class Vendeur extends Utilisateur {
    private String adresseLivraison;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coordonnees_bancaires_id")
    private CoordonneesBancaires coordonneesBancaires;
    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private List<Produit> produits = new ArrayList<>();
    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PanierDetails> panierDetails;

    @OneToMany(mappedBy = "vendeur", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Commande> commandes;
}
