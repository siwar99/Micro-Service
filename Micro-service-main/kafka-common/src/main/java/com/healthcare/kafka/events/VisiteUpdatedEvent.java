package com.healthcare.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisiteUpdatedEvent {
    private Long visiteId;
    private String statut;
    private String notes;
    private String prescription;
} 