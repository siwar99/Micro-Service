package com.healthcare.medecin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medecins")
public class Medecin {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private String specialite;
    private String email;
    private String telephone;
    private String adresse;
} 