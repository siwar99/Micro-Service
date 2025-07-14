package com.healthcare.medecin.service;

import com.healthcare.kafka.events.VisiteCreatedEvent;
import com.healthcare.kafka.events.VisiteUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "visite-created", groupId = "medecin-service-group")
    public void handleVisiteCreated(VisiteCreatedEvent event) {
        log.info("Nouvelle visite créée pour le médecin {}: Patient {} le {}", 
            event.getMedecinId(), 
            event.getNomPatient(), 
            event.getDateVisite());
        // Ici, vous pouvez ajouter la logique pour notifier le médecin
        // par exemple, envoyer un email ou une notification push
    }

    @KafkaListener(topics = "visite-updated", groupId = "medecin-service-group")
    public void handleVisiteUpdated(VisiteUpdatedEvent event) {
        log.info("Visite {} mise à jour. Nouveau statut : {}", 
            event.getVisiteId(), 
            event.getStatut());
        // Ici, vous pouvez ajouter la logique pour notifier le médecin
        // des mises à jour de la visite
    }
} 