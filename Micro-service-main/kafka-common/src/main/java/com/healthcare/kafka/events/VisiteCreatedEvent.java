package com.healthcare.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisiteCreatedEvent {
    private Long visiteId;
    private String medecinId;
    private String nomPatient;
    private LocalDateTime dateVisite;
    private String motif;
} 