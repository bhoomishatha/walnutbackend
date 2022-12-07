package com.walnutclinics.billingService.service;

import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.repository.DoctorRepository;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public Doctor getDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId).get();
    }

    public List<Doctor> getDoctorList() {
        return doctorRepository.findAll();
    }

    public List<DoctorBasicInfoResponse> getMatchingDoctor(String name) {
        return doctorRepository.getMatchingPatients(name);
    }

}
