package com.example.ecommerce.controllers;

import com.example.ecommerce.services.ICommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands")
public class CommandController {
    @Autowired
    ICommandService commandService;
    @PostMapping("/add/{idUser}")
    public ResponseEntity<String> addCommandeForUser(@PathVariable Long idUser) {
        boolean isCommandAdded = commandService.addCommande(idUser);
        if (isCommandAdded) {
            return ResponseEntity.ok("Commande ajoutée avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de la commande.");
        }
    }
}
