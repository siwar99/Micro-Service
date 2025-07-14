package com.healthcare.visite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.shared.dto.VisiteDTO;
import com.healthcare.visite.service.VisiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisiteController.class)
class VisiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisiteService visiteService;

    private VisiteDTO visiteDTO;

    @BeforeEach
    void setUp() {
        visiteDTO = VisiteDTO.builder()
                .id(1L)
                .medecinId("1")
                .patientId("1")
                .dateVisite(LocalDateTime.now())
                .motif("Consultation générale")
                .notes("RAS")
                .build();
    }

    @Test
    @WithMockUser(roles = {"MEDECIN", "PATIENT"})
    void getAllVisites_ShouldReturnListOfVisites() throws Exception {
        when(visiteService.getAllVisites()).thenReturn(Arrays.asList(visiteDTO));

        mockMvc.perform(get("/api/visites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(visiteDTO.getId()))
                .andExpect(jsonPath("$[0].motif").value(visiteDTO.getMotif()));
    }

    @Test
    @WithMockUser(roles = {"MEDECIN", "PATIENT"})
    void getVisiteById_WhenVisiteExists_ShouldReturnVisite() throws Exception {
        when(visiteService.getVisiteById(1L)).thenReturn(visiteDTO);

        mockMvc.perform(get("/api/visites/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(visiteDTO.getId()))
                .andExpect(jsonPath("$.motif").value(visiteDTO.getMotif()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void createVisite_WithValidData_ShouldReturnCreatedVisite() throws Exception {
        when(visiteService.createVisite(any(VisiteDTO.class))).thenReturn(visiteDTO);

        mockMvc.perform(post("/api/visites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visiteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(visiteDTO.getId()))
                .andExpect(jsonPath("$.motif").value(visiteDTO.getMotif()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void updateVisite_WithValidData_ShouldReturnUpdatedVisite() throws Exception {
        when(visiteService.updateVisite(eq(1L), any(VisiteDTO.class))).thenReturn(visiteDTO);

        mockMvc.perform(put("/api/visites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visiteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(visiteDTO.getId()))
                .andExpect(jsonPath("$.motif").value(visiteDTO.getMotif()));
    }

    @Test
    @WithMockUser(roles = "MEDECIN")
    void deleteVisite_WhenVisiteExists_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/visites/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllVisites_WithoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/visites"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "PATIENT")
    void createVisite_WithWrongRole_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/api/visites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(visiteDTO)))
                .andExpect(status().isForbidden());
    }
} 