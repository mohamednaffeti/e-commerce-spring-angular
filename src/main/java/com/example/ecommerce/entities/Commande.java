package com.example.ecommerce.entities;

import com.example.ecommerce.entities.enumerations.TypePaiement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PanierDetails> panierDetails;



    private LocalDateTime dateCommande = LocalDateTime.now();
    private double sommeCommande;

    @Enumerated(value = EnumType.STRING)
    private TypePaiement modePaiement;

    @ManyToOne(fetch = FetchType.EAGER)
    private Vendeur vendeur;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;



}
