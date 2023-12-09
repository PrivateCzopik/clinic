package com.example.clinic.demo;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ClinicServiceTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicService clinicService;

    @Test
    void testGetAllClinics() {
        List<Clinic> clinics = new ArrayList<>();

        clinics.add(Clinic.builder()
                .id(1L)
                .patientRegistration("Marek Kowal")
                .dataUpdate("20.06.2007")
                .visitCheck("29.06.2007")
                .build());

        clinics.add(Clinic.builder()
                .id(2L)
                .patientRegistration("John Smith")
                .dataUpdate("15.02.2004")
                .visitDeletion("15.02.2004")
                .build());

        when(clinicRepository.findAll()).thenReturn(clinics);

        List<Clinic> result = clinicService.getAllClinics();

        assertEquals(clinics, result);
    }

    @Test
    void testGetClinicById() {
        Clinic clinic = Clinic.builder()
                .id(2L)
                .patientRegistration("John Smith")
                .dataUpdate("15.02.2004")
                .visitDeletion("15.02.2004")
                .build();

        when(clinicRepository.findById(1L)).thenReturn(Optional.of(clinic));
        when(clinicRepository.findById(2L)).thenReturn(Optional.empty());

        Clinic resultTruthy = clinicService.getClinicById(1L);
        Clinic resultFalsy = clinicService.getClinicById(2L);

        assertEquals(clinic, resultTruthy);
        assertNull(resultFalsy);
    }

    @Test
    void testCreateClinic() {
        Clinic clinic = Clinic.builder()
                .id(2L)
                .patientRegistration("John Smith")
                .dataUpdate("15.02.2004")
                .visitDeletion("15.02.2004")
                .build();

        when(clinicRepository.save(clinic)).thenReturn(clinic);

        Clinic result = clinicService.createClinic(clinic);

        assertEquals(clinic, result);

        verify(clinicRepository, times(1)).save(eq(clinic));
    }

    @Test
    void testDeleteClinic() {
        Long validClinicId = 1L;
        Clinic clinicToDelete = new Clinic();
        when(clinicRepository.findById(validClinicId)).thenReturn(Optional.of(clinicToDelete));

        assertTrue(clinicService.deleteClinic(validClinicId));

        verify(clinicRepository, times(1)).deleteById(validClinicId);

        Long invalidClinicId = 2L;
        when(clinicRepository.findById(invalidClinicId)).thenReturn(Optional.empty());

        assertFalse(clinicService.deleteClinic(invalidClinicId));

        verify(clinicRepository, never()).deleteById(invalidClinicId);
    }
}
