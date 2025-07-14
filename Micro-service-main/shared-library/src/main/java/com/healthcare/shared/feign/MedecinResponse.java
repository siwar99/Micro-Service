package com.healthcare.shared.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedecinResponse {
    private String id;
    private String nom;
    private String prenom;
    private String specialite;
    private String email;
    private String telephone;
    private String adresse;
} 