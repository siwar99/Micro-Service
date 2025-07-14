package com.healthcare.visite.repository;

import com.healthcare.visite.model.Visite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisiteRepository extends JpaRepository<Visite, Long> {
    List<Visite> findByMedecinId(String medecinId);
} 