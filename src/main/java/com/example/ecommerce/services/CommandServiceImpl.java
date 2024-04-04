package com.example.ecommerce.services;

import com.example.ecommerce.Repositories.CommandeRepository;
import com.example.ecommerce.Repositories.PanierDetailsRepository;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.entities.enumerations.TypePaiement;
import com.example.ecommerce.exceptions.DataNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandServiceImpl implements ICommandService {
    @Autowired
    IPanierDetailsService panierDetailsService;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    PanierDetailsRepository panierDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public boolean addCommande(Long idUser) {
        Utilisateur utilisateur = userRepository.findById(idUser).orElseThrow(() -> new DataNotFoundException("Client not found"));
        double somme =0;
        Commande commande = Commande.builder()
                .dateCommande(LocalDateTime.now())
                .modePaiement(TypePaiement.Espece)
                .build();
        if(utilisateur instanceof Client){
            commande.setClient((Client) utilisateur);
        }else if(utilisateur instanceof Vendeur){
            commande.setVendeur((Vendeur) utilisateur);
        }
        List<PanierDetails> panierDetailsNonCommanderByUser = panierDetailsService.getAllByUserNonCommander(idUser);
        commande.setPanierDetails(panierDetailsNonCommanderByUser);

        for (PanierDetails panierDetails : panierDetailsNonCommanderByUser) {
            panierDetails.setCommande(commande);
            somme+=panierDetails.getQuantite()*panierDetails.getProduit().getPrice();
        }
        commande.setSommeCommande(somme);
        commandeRepository.save(commande);
        panierDetailsRepository.saveAll(panierDetailsNonCommanderByUser);

        return true;
    }
}
