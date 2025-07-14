package com.healthcare.visite.controller;

import com.healthcare.shared.dto.VisiteDTO;
import com.healthcare.visite.service.VisiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visites")
@RequiredArgsConstructor
public class VisiteController {
    private final VisiteService visiteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'PATIENT')")
    public ResponseEntity<List<VisiteDTO>> getAllVisites() {
        return ResponseEntity.ok(visiteService.getAllVisites());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDECIN', 'PATIENT')")
    public ResponseEntity<VisiteDTO> getVisiteById(@PathVariable Long id) {
        return ResponseEntity.ok(visiteService.getVisiteById(id));
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<VisiteDTO>> getVisitesByMedecinId(@PathVariable String medecinId) {
        return ResponseEntity.ok(visiteService.getVisitesByMedecinId(medecinId));
    }

    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<VisiteDTO> createVisite(@Valid @RequestBody VisiteDTO visiteDTO) {
        return new ResponseEntity<>(visiteService.createVisite(visiteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<VisiteDTO> updateVisite(
            @PathVariable Long id,
            @Valid @RequestBody VisiteDTO visiteDTO) {
        return ResponseEntity.ok(visiteService.updateVisite(id, visiteDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Void> deleteVisite(@PathVariable Long id) {
        visiteService.deleteVisite(id);
        return ResponseEntity.noContent().build();
    }
} 