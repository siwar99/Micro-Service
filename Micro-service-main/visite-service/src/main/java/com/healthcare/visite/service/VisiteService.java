package com.healthcare.visite.service;

import com.healthcare.shared.event.VisiteCreatedEvent;
import com.healthcare.shared.event.VisiteUpdatedEvent;
import com.healthcare.shared.feign.MedecinClient;
import com.healthcare.shared.feign.MedecinResponse;
import com.healthcare.shared.dto.VisiteDTO;
import com.healthcare.visite.model.Visite;
import com.healthcare.visite.repository.VisiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisiteService {
    private final VisiteRepository visiteRepository;
    private final MedecinClient medecinClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<VisiteDTO> getAllVisites() {
        return visiteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VisiteDTO getVisiteById(Long id) {
        return visiteRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Visite non trouvée"));
    }

    public List<VisiteDTO> getVisitesByMedecinId(String medecinId) {
        return visiteRepository.findByMedecinId(medecinId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VisiteDTO createVisite(VisiteDTO visiteDTO) {
        // Temporairement désactivé pour le débogage
        // medecinClient.getMedecinById(visiteDTO.getMedecinId());
        
        Visite visite = convertToEntity(visiteDTO);
        visite = visiteRepository.save(visite);
        
        VisiteCreatedEvent event = new VisiteCreatedEvent(
            visite.getId(),
            visite.getMedecinId(),
            visite.getPatientId(),
            visite.getNomPatient(),
            visite.getDateVisite(),
            visite.getMotif(),
            visite.getNotes(),
            visite.getPrescription(),
            visite.getStatut()
        );
        
        kafkaTemplate.send("visite-created", event);
        return convertToDTO(visite);
    }

    public VisiteDTO updateVisite(Long id, VisiteDTO visiteDTO) {
        // Vérifier si le médecin existe
        MedecinResponse medecin = medecinClient.getMedecinById(visiteDTO.getMedecinId());
        if (medecin == null) {
            throw new RuntimeException("Médecin non trouvé");
        }

        Visite visite = visiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visite non trouvée"));
        
        visite.setMedecinId(visiteDTO.getMedecinId());
        visite.setPatientId(visiteDTO.getPatientId());
        visite.setNomPatient(visiteDTO.getNomPatient());
        visite.setDateVisite(visiteDTO.getDateVisite());
        visite.setMotif(visiteDTO.getMotif());
        visite.setNotes(visiteDTO.getNotes());
        visite.setPrescription(visiteDTO.getPrescription());
        visite.setStatut(visiteDTO.getStatut());
        
        visite = visiteRepository.save(visite);
        
        // Publier l'événement de mise à jour
        VisiteUpdatedEvent event = new VisiteUpdatedEvent(
            visite.getId(),
            visite.getMedecinId(),
            visite.getPatientId(),
            visite.getNomPatient(),
            visite.getDateVisite(),
            visite.getMotif(),
            visite.getNotes(),
            visite.getPrescription(),
            visite.getStatut()
        );
        kafkaTemplate.send("visite-updated", event);
        
        return convertToDTO(visite);
    }

    public void deleteVisite(Long id) {
        if (!visiteRepository.existsById(id)) {
            throw new RuntimeException("Visite non trouvée");
        }
        visiteRepository.deleteById(id);
    }

    private VisiteDTO convertToDTO(Visite visite) {
        return VisiteDTO.builder()
                .id(visite.getId())
                .medecinId(visite.getMedecinId())
                .patientId(visite.getPatientId())
                .nomPatient(visite.getNomPatient())
                .dateVisite(visite.getDateVisite())
                .motif(visite.getMotif())
                .notes(visite.getNotes())
                .prescription(visite.getPrescription())
                .statut(visite.getStatut())
                .build();
    }

    private Visite convertToEntity(VisiteDTO dto) {
        return Visite.builder()
                .id(dto.getId())
                .medecinId(dto.getMedecinId())
                .patientId(dto.getPatientId())
                .nomPatient(dto.getNomPatient())
                .dateVisite(dto.getDateVisite())
                .motif(dto.getMotif())
                .notes(dto.getNotes())
                .prescription(dto.getPrescription())
                .statut(dto.getStatut())
                .build();
    }
} 