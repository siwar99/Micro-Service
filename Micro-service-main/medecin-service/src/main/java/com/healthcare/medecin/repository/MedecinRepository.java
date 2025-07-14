package com.healthcare.medecin.repository;

import com.healthcare.medecin.model.Medecin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedecinRepository extends MongoRepository<Medecin, String> {
} 