package com.healthcare.shared.mapper;

import com.healthcare.shared.dto.MedecinDTO;
import com.healthcare.shared.feign.MedecinResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedecinMapper {
    
    MedecinMapper INSTANCE = Mappers.getMapper(MedecinMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "prenom", source = "prenom")
    @Mapping(target = "specialite", source = "specialite")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telephone", source = "telephone")
    @Mapping(target = "adresse", source = "adresse")
    MedecinResponse toResponse(MedecinDTO medecin);
} 