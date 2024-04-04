package com.example.ecommerce.controllers;

import com.example.ecommerce.entities.PanierDetails;
import com.example.ecommerce.entities.dto.CommandByDateDTO;
import com.example.ecommerce.services.IPanierDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/panierDetails")
public class PanierDetailsController {
    @Autowired
    private IPanierDetailsService panierDetailsService;

    @PostMapping(value = "/create/{idClient}/{idProduit}/{qte}")
    public ResponseEntity<PanierDetails> addToPanier(@PathVariable Long idClient, @PathVariable Long idProduit , @PathVariable int qte){
        return ResponseEntity.ok(panierDetailsService.addToPanier(idClient,idProduit,qte));
    }

    @GetMapping("/getByUser/{idClient}")
    public ResponseEntity<List<PanierDetails>> getAllByUser(@PathVariable Long idClient) {
        List<PanierDetails> panierDetailsList = panierDetailsService.getAllByUser(idClient);
        if (panierDetailsList != null) {
            return ResponseEntity.ok(panierDetailsList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{idPanierDetails}")
    public ResponseEntity<Void> deletePanierDetail(@PathVariable Long idPanierDetails) {
        panierDetailsService.deletePanierDetail(idPanierDetails);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{idPanierDetails}/{newQte}")
    public ResponseEntity<PanierDetails> updateQte(@PathVariable Long idPanierDetails, @PathVariable int newQte) {
        PanierDetails updatedPanierDetails = panierDetailsService.updateQte(idPanierDetails, newQte);
        return ResponseEntity.ok(updatedPanierDetails);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<PanierDetails>> getAllPanierDetails() {
        List<PanierDetails> panierDetailsList = panierDetailsService.getAll();
        if (!panierDetailsList.isEmpty()) {
            return ResponseEntity.ok(panierDetailsList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getByUserNonCommander/{idUser}")
    public ResponseEntity<List<PanierDetails>> getAllByUserNonCommander(@PathVariable Long idUser) {
        List<PanierDetails> panierDetailsList = panierDetailsService.getAllByUserNonCommander(idUser);
        return ResponseEntity.ok(panierDetailsList);
    }
    @GetMapping("/getByUserCommander/{idUser}")
    public ResponseEntity<List<PanierDetails>> getAllByUserCommander(@PathVariable Long idUser) {
        List<PanierDetails> panierDetailsList = panierDetailsService.getAllByUserCommander(idUser);
        return ResponseEntity.ok(panierDetailsList);
    }
    @GetMapping("/groupByDateCommande/{idUser}")
    public ResponseEntity<List<CommandByDateDTO>> getGroupingByDateCommandeByUser(@PathVariable Long idUser) {
        Map<LocalDateTime, List<PanierDetails>> groupingByDate = panierDetailsService.getGroupingByDateCommandeByUser(idUser);
        if (!groupingByDate.isEmpty()) {
            List<CommandByDateDTO> commandByDateDTOList = new ArrayList<>();
            for (Map.Entry<LocalDateTime, List<PanierDetails>> entry : groupingByDate.entrySet()) {
                LocalDateTime dateCommande = entry.getKey();
                List<PanierDetails> panierDetailsList = entry.getValue();
                CommandByDateDTO commandByDateDTO = new CommandByDateDTO(dateCommande, panierDetailsList);
                commandByDateDTOList.add(commandByDateDTO);
            }
            return ResponseEntity.ok(commandByDateDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
