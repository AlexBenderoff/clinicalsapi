package com.alex.clinacals.controllers;

import com.alex.clinacals.dto.ClinicalDataRequest;
import com.alex.clinacals.model.ClinicalData;
import com.alex.clinacals.model.Patient;
import com.alex.clinacals.repositories.ClinicalDataRepository;
import com.alex.clinacals.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {


    private final ClinicalDataRepository clinicalDataRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
        this.clinicalDataRepository = clinicalDataRepository;
        this.patientRepository = patientRepository;
    }


    @PostMapping(value = "/clinicals")
    public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request){
        Patient patient = patientRepository.findById(request.getPatientId()).get();

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(request.getComponentName());
        clinicalData.setComponentValue(request.getComponentValue());

        clinicalData.setPatient(patient);

        return clinicalDataRepository.save(clinicalData);

    }
}
