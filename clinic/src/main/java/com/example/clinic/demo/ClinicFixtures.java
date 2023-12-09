package com.example.clinic.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClinicFixtures {

    @Bean
    public CommandLineRunner initDatabase(ClinicRepository clinicRepository) {
        return args -> {
            clinicRepository.save(Clinic.builder()
                    .id(1L)
                    .patientRegistration("Marek Kowal")
                    .dataUpdate("20.06.2007")
                    .visitCheck("29.06.2007")
                    .build()
            );

            clinicRepository.save(Clinic.builder()
                    .id(2L)
                    .patientRegistration("John Smith")
                    .dataUpdate("15.02.2004")
                    .visitDeletion("15.02.2004")
                    .build()
            );
        };
    }
}