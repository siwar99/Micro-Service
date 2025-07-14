package com.healthcare.shared.mapper;

import com.healthcare.shared.dto.MedecinDTO;
import com.healthcare.shared.feign.MedecinResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T00:56:48+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class MedecinMapperImpl implements MedecinMapper {

    @Override
    public MedecinResponse toResponse(MedecinDTO medecin) {
        if ( medecin == null ) {
            return null;
        }

        MedecinResponse.MedecinResponseBuilder medecinResponse = MedecinResponse.builder();

        medecinResponse.id( medecin.getId() );
        medecinResponse.nom( medecin.getNom() );
        medecinResponse.prenom( medecin.getPrenom() );
        medecinResponse.specialite( medecin.getSpecialite() );
        medecinResponse.email( medecin.getEmail() );
        medecinResponse.telephone( medecin.getTelephone() );
        medecinResponse.adresse( medecin.getAdresse() );

        return medecinResponse.build();
    }
}
