package com.example.ecommerce.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data

@NoArgsConstructor

@ToString
@DiscriminatorValue("Client")
public class Client extends Utilisateur {
    private String adresseLivraison;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PanierDetails> panierDetails;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Commande> commandes;

}