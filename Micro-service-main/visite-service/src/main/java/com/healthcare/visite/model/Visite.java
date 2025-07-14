package com.healthcare.visite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visites")
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String medecinId;
    
    @Column(nullable = false)
    private String patientId;
    
    @Column(nullable = false)
    private String nomPatient;
    
    @Column(nullable = false)
    private LocalDateTime dateVisite;
    
    @Column(nullable = false)
    private String motif;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(columnDefinition = "TEXT")
    private String prescription;
    
    @Column(nullable = false)
    private String statut;

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
} 