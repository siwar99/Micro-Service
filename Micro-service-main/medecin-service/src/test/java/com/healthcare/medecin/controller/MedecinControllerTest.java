package com.healthcare.medecin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.medecin.service.MedecinService;
import com.healthcare.medecin.dto.MedecinDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedecinController.class)
@Import(MedecinControllerTest.TestSecurityConfig.class)
class MedecinControllerTest {

    @EnableWebSecurity
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/medecins/**").hasAnyRole("MEDECIN", "PATIENT")
                    .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> {})
                );
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedecinService medecinService;

    private MedecinDTO medecinDTO;
    private List<MedecinDTO> medecinList;

    @BeforeEach
    void setUp() {
        medecinDTO = MedecinDTO.builder()
                .id("1")
                .nom("Dupont")
                .prenom("Jean")
                .specialite("Généraliste")
                .email("jean.dupont@example.com")
                .telephone("+33123456789")
                .adresse("123 rue de la Santé, Paris")
                .build();
        
        medecinList = Arrays.asList(medecinDTO);
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void getAllMedecins_ShouldReturnListOfMedecins() throws Exception {
        when(medecinService.getAllMedecins()).thenReturn(medecinList);

        mockMvc.perform(get("/api/medecins"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(medecinDTO.getId()))
                .andExpect(jsonPath("$[0].nom").value(medecinDTO.getNom()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void getMedecinById_WhenMedecinExists_ShouldReturnMedecin() throws Exception {
        when(medecinService.getMedecinById("1")).thenReturn(medecinDTO);

        mockMvc.perform(get("/api/medecins/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(medecinDTO.getId()))
                .andExpect(jsonPath("$.nom").value(medecinDTO.getNom()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void createMedecin_WithValidData_ShouldReturnCreatedMedecin() throws Exception {
        when(medecinService.createMedecin(any(MedecinDTO.class))).thenReturn(medecinDTO);

        mockMvc.perform(post("/api/medecins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medecinDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(medecinDTO.getId()))
                .andExpect(jsonPath("$.nom").value(medecinDTO.getNom()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void updateMedecin_WithValidData_ShouldReturnUpdatedMedecin() throws Exception {
        when(medecinService.updateMedecin(eq("1"), any(MedecinDTO.class))).thenReturn(medecinDTO);

        mockMvc.perform(put("/api/medecins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medecinDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(medecinDTO.getId()))
                .andExpect(jsonPath("$.nom").value(medecinDTO.getNom()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void deleteMedecin_WhenMedecinExists_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/medecins/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllMedecins_WithoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/medecins"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void getAllMedecins_WithWrongRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/medecins"))
                .andExpect(status().isForbidden());
    }
} 