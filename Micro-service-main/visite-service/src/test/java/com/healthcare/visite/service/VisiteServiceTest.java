package com.healthcare.visite.service;

import com.healthcare.kafka.events.VisiteCreatedEvent;
import com.healthcare.kafka.events.VisiteUpdatedEvent;
import com.healthcare.shared.dto.VisiteDTO;
import com.healthcare.visite.model.Visite;
import com.healthcare.visite.repository.VisiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisiteServiceTest {

    @Mock
    private VisiteRepository visiteRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private VisiteService visiteService;

    private Visite visite;
    private VisiteDTO visiteDTO;

    @BeforeEach
    void setUp() {
        visite = Visite.builder()
                .id(1L)
                .medecinId("1")
                .patientId("1")
                .dateVisite(LocalDateTime.now())
                .motif("Consultation générale")
                .notes("RAS")
                .build();

        visiteDTO = VisiteDTO.builder()
                .id(1L)
                .medecinId("1")
                .patientId("1")
                .dateVisite(visite.getDateVisite())
                .motif("Consultation générale")
                .notes("RAS")
                .build();
    }

    @Test
    void getAllVisites_ShouldReturnListOfVisites() {
        when(visiteRepository.findAll()).thenReturn(Arrays.asList(visite));

        List<VisiteDTO> result = visiteService.getAllVisites();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMotif()).isEqualTo(visite.getMotif());
        verify(visiteRepository, times(1)).findAll();
    }

    @Test
    void getVisiteById_WhenVisiteExists_ShouldReturnVisite() {
        when(visiteRepository.findById(1L)).thenReturn(Optional.of(visite));

        VisiteDTO result = visiteService.getVisiteById(1L);

        assertThat(result.getId()).isEqualTo(visite.getId());
        assertThat(result.getMotif()).isEqualTo(visite.getMotif());
        verify(visiteRepository, times(1)).findById(1L);
    }

    @Test
    void getVisiteById_WhenVisiteDoesNotExist_ShouldThrowException() {
        when(visiteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> visiteService.getVisiteById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Visite non trouvée");
    }

    @Test
    void createVisite_ShouldReturnCreatedVisite() {
        when(visiteRepository.save(any(Visite.class))).thenReturn(visite);
        when(kafkaTemplate.send(anyString(), any(VisiteCreatedEvent.class))).thenReturn(null);

        VisiteDTO result = visiteService.createVisite(visiteDTO);

        assertThat(result.getMotif()).isEqualTo(visiteDTO.getMotif());
        verify(visiteRepository, times(1)).save(any(Visite.class));
        verify(kafkaTemplate, times(1)).send(eq("visite-created"), any(VisiteCreatedEvent.class));
    }

    @Test
    void updateVisite_WhenVisiteExists_ShouldReturnUpdatedVisite() {
        when(visiteRepository.findById(1L)).thenReturn(Optional.of(visite));
        when(visiteRepository.save(any(Visite.class))).thenReturn(visite);
        when(kafkaTemplate.send(anyString(), any(VisiteUpdatedEvent.class))).thenReturn(null);

        VisiteDTO result = visiteService.updateVisite(1L, visiteDTO);

        assertThat(result.getMotif()).isEqualTo(visiteDTO.getMotif());
        verify(visiteRepository, times(1)).save(any(Visite.class));
        verify(kafkaTemplate, times(1)).send(eq("visite-updated"), any(VisiteUpdatedEvent.class));
    }

    @Test
    void updateVisite_WhenVisiteDoesNotExist_ShouldThrowException() {
        when(visiteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> visiteService.updateVisite(1L, visiteDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Visite non trouvée");
    }

    @Test
    void deleteVisite_WhenVisiteExists_ShouldDeleteVisite() {
        when(visiteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(visiteRepository).deleteById(1L);

        visiteService.deleteVisite(1L);

        verify(visiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteVisite_WhenVisiteDoesNotExist_ShouldThrowException() {
        when(visiteRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> visiteService.deleteVisite(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Visite non trouvée");
    }
} 