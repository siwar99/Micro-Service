package com.healthcare.medecin.service;

import com.healthcare.medecin.dto.MedecinDTO;
import com.healthcare.medecin.model.Medecin;
import com.healthcare.medecin.repository.MedecinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;

    public List<MedecinDTO> getAllMedecins() {
        return medecinRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MedecinDTO getMedecinById(String id) {
        return medecinRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));
    }

    public MedecinDTO createMedecin(MedecinDTO medecinDTO) {
        Medecin medecin = convertToEntity(medecinDTO);
        return convertToDTO(medecinRepository.save(medecin));
    }

    public MedecinDTO updateMedecin(String id, MedecinDTO medecinDTO) {
        if (!medecinRepository.existsById(id)) {
            throw new RuntimeException("Médecin non trouvé");
        }
        Medecin medecin = convertToEntity(medecinDTO);
        medecin.setId(id);
        return convertToDTO(medecinRepository.save(medecin));
    }

    public void deleteMedecin(String id) {
        if (!medecinRepository.existsById(id)) {
            throw new RuntimeException("Médecin non trouvé");
        }
        medecinRepository.deleteById(id);
    }

    private MedecinDTO convertToDTO(Medecin medecin) {
        return MedecinDTO.builder()
                .id(medecin.getId())
                .nom(medecin.getNom())
                .prenom(medecin.getPrenom())
                .specialite(medecin.getSpecialite())
                .email(medecin.getEmail())
                .telephone(medecin.getTelephone())
                .adresse(medecin.getAdresse())
                .build();
    }

    private Medecin convertToEntity(MedecinDTO dto) {
        return Medecin.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .specialite(dto.getSpecialite())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .build();
    }
} 