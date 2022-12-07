package com.walnutclinics.billingService.controller;

import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import com.walnutclinics.billingService.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/doctors")
@RestController

public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/allDoctors")
    public List<Doctor> getAllDoctors() {
        return doctorService.getDoctorList();
    }

    @PostMapping("/save")
    public Doctor saveDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @PatchMapping("/update")
    public Doctor editDoctor(@RequestBody Doctor doctor) {
        return doctorService.updateDoctor(doctor);

    }

    @DeleteMapping("/delete")
    public void deleteDoctor(@RequestParam long doctorId) {
        doctorService.deleteDoctor(doctorId);

    }

    @GetMapping("getMappedClinics")
    public List<Clinic> getMappedClinic(@RequestParam int clinicId) {
        return null;

    }

    @GetMapping("getMappedTreatments")
    public List<Treatment> getMappedTreatments(@RequestParam int treatmentId) {
        return null;
    }

//    @GetMapping("getMappedClinicsBasedOnClinicAndPatient")

    @GetMapping("/getMatchingDoctor")
    public List<DoctorBasicInfoResponse> matchingClinic(@RequestParam String doctorName) {
        return doctorService.getMatchingDoctor(doctorName);
    }

    @GetMapping
    public Doctor getDoctor(@RequestParam long doctorId) {
        return doctorService.getDoctor(doctorId);
    }

}

