package com.healthcare.shared.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisiteCreatedEvent {
    private Long id;
    private String medecinId;
    private String patientId;
    private String nomPatient;
    private LocalDateTime dateVisite;
    private String motif;
    private String notes;
    private String prescription;
    private String statut;
} 