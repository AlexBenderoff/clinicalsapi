package com.alex.clinacals.controllers;

import com.alex.clinacals.model.ClinicalData;
import com.alex.clinacals.model.Patient;
import com.alex.clinacals.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin
public class PatientController {

    private final PatientRepository repository;
    private Map<String, String> filters = new HashMap<>();

    @Autowired
    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/patients")
    public List<Patient> getPatients(){
        return repository.findAll();
    }

    @GetMapping(path = "/patients/{id}")
    public Patient getPatient(@PathVariable int id){
        return repository.findById(id).get();
    }

    @PostMapping(path = "/patients")
    public Patient savePatient(@RequestBody Patient patient){
        return repository.save(patient);
    }

    @GetMapping(value = "/patients/analyze/{id}")
    public Patient analyze(@PathVariable int id){
        Patient patient = repository.findById(id).get();

        List<ClinicalData> clinicalData = patient.getClinicalData();

        ArrayList<ClinicalData> dublicateClinicalDate = new ArrayList<>(clinicalData);
        for (ClinicalData eachEntry : dublicateClinicalDate) {
            if (eachEntry.getComponentName().equals("hw")) {

                if (filters.containsKey(eachEntry.getComponentName())) {
                    clinicalData.remove(eachEntry);
                    continue;
                }
                else
                {
                    filters.put(eachEntry.getComponentName(), null);
                }

                if (eachEntry.getComponentName().equals("hw")){

                    String[] heightAndWeight = eachEntry.getComponentValue().split("/");

                    if (heightAndWeight != null && heightAndWeight.length > 1) {

                        float heightInMetres = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
                        float bmi = Float.parseFloat(heightAndWeight[1]) / (heightInMetres * heightInMetres);
                        ClinicalData bmiData = new ClinicalData();
                        bmiData.setComponentName("bmi");
                        bmiData.setComponentValue(Float.toString(bmi));
                        clinicalData.add(bmiData);
                    }
                }

            }

        }

        filters.clear();
        return patient;
    }
}
