package com.example.ecommerce.entities.dto;

import com.example.ecommerce.entities.PanierDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandByDateDTO {
    private LocalDateTime dateCommande;
    List<PanierDetails> panierDetailsList;
}
