package com.walnutclinics.billingService.service;

import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.repository.ClinicRepository;
import com.walnutclinics.billingService.response.ClinicBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public Clinic saveClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public Clinic updateClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public void deleteClinic(Long clinicId) {
        clinicRepository.deleteById(clinicId);
    }

    public Clinic viewClinic(Long clinicId) {
        return clinicRepository.findById(clinicId).get();
    }

    public List<Clinic> getAllClinic() {
        return clinicRepository.findAll();
    }

    public List<ClinicBasicInfoResponse> getMatchingClinic(String name) {
        return clinicRepository.getMatchingClinic(name);
    }


}
