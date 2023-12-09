package com.example.clinic.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ClinicController.class)
class ClinicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClinicService clinicService;

    @Test
    void shouldReturnAllClinics() throws Exception {
        Clinic clinic1 = Clinic.builder()
                .id(1L)
                .patientRegistration("Marek Kowal")
                .dataUpdate("20.06.2007")
                .visitCheck("29.06.2007")
                .build();

        Clinic clinic2 = Clinic.builder()
                .id(2L)
                .patientRegistration("John Smith")
                .dataUpdate("15.02.2004")
                .visitDeletion("15.02.2004")
                .build();

        when(clinicService.getAllClinics()).thenReturn(Arrays.asList(clinic1, clinic2));

        mockMvc.perform(get("/api/clinics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].patientRegistration").value("Marek Kowal"))
                .andExpect(jsonPath("$[1].patientRegistration").value("John Smith"));
    }

    @Test
    void shouldReturnClinicById() throws Exception {
        Clinic clinic = Clinic.builder()
                .id(1L)
                .patientRegistration("Marek Kowal")
                .dataUpdate("20.06.2007")
                .visitCheck("29.06.2007")
                .build();
        when(clinicService.getClinicById(1L)).thenReturn(clinic);

        mockMvc.perform(get("/api/clinics/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientRegistration").value("Marek Kowal"));
    }
}
