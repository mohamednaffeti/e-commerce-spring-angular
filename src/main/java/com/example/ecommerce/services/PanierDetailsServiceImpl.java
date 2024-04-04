package com.example.ecommerce.services;

import com.example.ecommerce.Repositories.PanierDetailsRepository;
import com.example.ecommerce.Repositories.ProduitRepository;
import com.example.ecommerce.Repositories.StockRepository;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.*;
import com.example.ecommerce.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PanierDetailsServiceImpl implements IPanierDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private PanierDetailsRepository panierDetailsRepository;
    @Autowired
    private StockRepository stockRepository;
    @Override
    public PanierDetails addToPanier(Long idClient, Long idProduit, int qte) {
        Utilisateur utilisateur = userRepository.findById(idClient)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Produit produit = produitRepository.findById(idProduit)
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        if (produit.getStock().getQuantity() < qte) {
            throw new DataNotFoundException("Quantity not available in stock");
        }

        PanierDetails panierDetails = PanierDetails.builder()
                .produit(produit)
                .quantite(qte)
                .build();

        if (utilisateur instanceof Client) {
            panierDetails.setClient((Client) utilisateur);
        } else if (utilisateur instanceof Vendeur) {
            panierDetails.setVendeur((Vendeur) utilisateur);
        }

        updateStock(idProduit, qte);
        return panierDetailsRepository.save(panierDetails);
    }

    @Override
    public List<PanierDetails> getAll() {
        return panierDetailsRepository.findAll();
    }

    @Override
    public List<PanierDetails> getAllByUser(Long idClient) {
        Utilisateur utilisateur = userRepository.findById(idClient).orElseThrow(() -> new DataNotFoundException("Client not found"));

            if(utilisateur instanceof Client){
                return panierDetailsRepository.findByClientId(idClient);
            }else if(utilisateur instanceof Vendeur){
                return panierDetailsRepository.findByVendeurId(idClient);
            }else{
                throw new DataNotFoundException("should be a client or Vendor");

        }
    }

    @Override
    public List<PanierDetails> getAllByUserNonCommander(Long idUser) {
        return getAllByUser(idUser)
                .stream()
                .filter(panierDetails -> panierDetails.getCommande()==null)
                .toList();
    }

    @Override
    public List<PanierDetails> getAllByUserCommander(Long idUser) {
        return getAllByUser(idUser)
                .stream()
                .filter(panierDetails -> panierDetails.getCommande()!= null)
                .toList();
    }

    @Override
    public Map<LocalDateTime, List<PanierDetails>> getGroupingByDateCommandeByUser(Long idUser) {
        userRepository.findById(idUser).orElseThrow(() -> new DataNotFoundException("Client not found"));
        List<PanierDetails> panierDetails=this.getAllByUserCommander(idUser);
        return panierDetails.stream()
                    .collect(Collectors
                            .groupingBy(panierDetails1 -> panierDetails1.getCommande().getDateCommande()));

    }

    @Override
    public void deletePanierDetail(Long idPanier) {
        panierDetailsRepository.deleteById(idPanier);
    }

    @Override
    public PanierDetails updateQte(Long idPanierDetails, int newQte) {
        PanierDetails panierDetails = panierDetailsRepository.findById(idPanierDetails).orElse(null);
        if(panierDetails == null){
            throw new DataNotFoundException("panier detail not found");
        }else{
            int qteexistant = panierDetails.getQuantite()+panierDetails.getProduit().getStock().getQuantity();
            System.out.println(qteexistant);
            if(newQte>qteexistant){
                throw new DataNotFoundException("Quantity not available");
            }else{
               int qteupdated = newQte-panierDetails.getQuantite();
               updateStock(panierDetails.getProduit().getIdProduit(),qteupdated);
               panierDetails.setQuantite(newQte);
              return panierDetailsRepository.save(panierDetails);

            }

        }

    }


    void updateStock(Long idProduit,int qte){
        Stock stock = stockRepository.findByProduitIdProduit(idProduit);
        if(stock == null){
            throw new DataNotFoundException("stock not fount");
        }else{
            stock.setQuantity(stock.getQuantity()-qte);
            stock.setAvailable(stock.getQuantity() != 0);

        }
        stockRepository.save(stock);
    }
}
