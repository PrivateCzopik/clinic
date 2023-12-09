package com.example.clinic.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    public Clinic getClinicById(Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    public Clinic createClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public Clinic updateClinic(Long id, Clinic updatedClinic) {
        Clinic existingClinic = clinicRepository.findById(id).orElse(null);

        if (existingClinic != null) {
            existingClinic.setPatientRegistration(updatedClinic.getPatientRegistration());
            existingClinic.setDataUpdate(updatedClinic.getDataUpdate());
            existingClinic.setVisitDeletion(updatedClinic.getVisitDeletion());
            existingClinic.setVisitCheck(updatedClinic.getVisitCheck());
            return clinicRepository.save(existingClinic);
        }
        return null;
    }

    public boolean deleteClinic(Long id) {
        Optional<Clinic> optionalUser = clinicRepository.findById(id);

        if (optionalUser.isPresent()) {
            clinicRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
