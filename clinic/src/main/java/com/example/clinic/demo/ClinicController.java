package com.example.clinic.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicRepository clinicRepository;

    public ClinicController(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @GetMapping
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    @GetMapping("/{id}")
    public Clinic getClinicById(@PathVariable Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Clinic createClinic(@RequestBody Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    @PutMapping("/{id}")
    public Clinic updateClinic(@PathVariable Long id, @RequestBody Clinic updatedClinic) {
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

    @DeleteMapping("/{id}")
    public void deleteClinic(@PathVariable Long id) {
        clinicRepository.deleteById(id);
    }
}
