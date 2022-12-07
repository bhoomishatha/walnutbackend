package com.walnutclinics.billingService.service;

import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.repository.TreatmentRepository;
import com.walnutclinics.billingService.response.TreatmentBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

    @Autowired
    TreatmentRepository treatmentRepository;

    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public Treatment updateTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public List<Treatment> getAllTreatment() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatment(Long id) {
        return treatmentRepository.getReferenceById(id);
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }

    public List<TreatmentBasicInfoResponse> getMatchingTreatment(String name)
    {
        return treatmentRepository.getMatchingTreatment(name);
    }
}
