package com.healthcare.medecin.controller;

import com.healthcare.medecin.dto.MedecinDTO;
import com.healthcare.medecin.service.MedecinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {
    private final MedecinService medecinService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'PATIENT')")
    public ResponseEntity<List<MedecinDTO>> getAllMedecins() {
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'PATIENT')")
    public ResponseEntity<MedecinDTO> getMedecinById(@PathVariable String id) {
        return ResponseEntity.ok(medecinService.getMedecinById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<MedecinDTO> createMedecin(@Valid @RequestBody MedecinDTO medecinDTO) {
        return new ResponseEntity<>(medecinService.createMedecin(medecinDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<MedecinDTO> updateMedecin(
            @PathVariable String id,
            @Valid @RequestBody MedecinDTO medecinDTO) {
        return ResponseEntity.ok(medecinService.updateMedecin(id, medecinDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Void> deleteMedecin(@PathVariable String id) {
        medecinService.deleteMedecin(id);
        return ResponseEntity.noContent().build();
    }
} 