package com.alex.clinacals.repositories;

import com.alex.clinacals.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
