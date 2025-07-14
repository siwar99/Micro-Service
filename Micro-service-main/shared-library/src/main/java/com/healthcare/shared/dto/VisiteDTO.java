package com.healthcare.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisiteDTO {
    private Long id;
    
    @NotBlank(message = "L'ID du médecin est obligatoire")
    private String medecinId;
    
    @NotBlank(message = "L'ID du patient est obligatoire")
    private String patientId;
    
    @NotBlank(message = "Le nom du patient est obligatoire")
    private String nomPatient;
    
    @NotNull(message = "La date de visite est obligatoire")
    private LocalDateTime dateVisite;
    
    @NotBlank(message = "Le motif de la visite est obligatoire")
    private String motif;
    
    private String notes;
    
    private String prescription;
    
    @Builder.Default
    private String statut = "PLANIFIE";
} 