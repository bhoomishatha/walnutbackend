package com.walnutclinics.billingService.controller;

import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import com.walnutclinics.billingService.response.TreatmentBasicInfoResponse;
import com.walnutclinics.billingService.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/v1/treatment")
public class TreatmentController {

    @Autowired
    TreatmentService treatmentService;

    @GetMapping("/getTreatmentList")
    public List<Treatment> getClinics() {
        return treatmentService.getAllTreatment();
    }

    @PostMapping("/save")
    public Treatment saveClinic(@RequestBody Treatment treatment) {
        return treatmentService.saveTreatment(treatment);
    }

    @PatchMapping("/update")
    public Treatment editClinic(@RequestBody Treatment treatment) {
        return treatmentService.updateTreatment(treatment);

    }

    @DeleteMapping("/delete")
    public void deleteClinic(@RequestParam long treatmentId) {
        treatmentService.deleteTreatment(treatmentId);
    }

    @GetMapping("/getMatchingTreatment")
    public List<TreatmentBasicInfoResponse> matchingTreatment(@RequestParam String treatmentName) {
        return treatmentService.getMatchingTreatment(treatmentName);
    }

    @GetMapping
    public Treatment getTreatment(long treatmentId)
    {
        return treatmentService.getTreatment(treatmentId);
    }

}
