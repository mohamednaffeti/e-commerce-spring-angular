package com.example.ecommerce.services;

import com.example.ecommerce.entities.PanierDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IPanierDetailsService {
    PanierDetails addToPanier(Long idClient,Long idProduit,int qte);
    List<PanierDetails> getAll();
    List<PanierDetails> getAllByUser(Long idClient);
    List<PanierDetails> getAllByUserNonCommander(Long idUser);
    List<PanierDetails> getAllByUserCommander(Long idUser);
    Map<LocalDateTime,List<PanierDetails>> getGroupingByDateCommandeByUser(Long idUser);
    void deletePanierDetail(Long idPanier);
    PanierDetails updateQte(Long idPanierDetails,int newQte);
}
