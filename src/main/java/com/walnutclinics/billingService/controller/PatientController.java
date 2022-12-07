package com.walnutclinics.billingService.controller;

import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.model.patient.Patient;
import com.walnutclinics.billingService.request.PatientSaveRequest;
import com.walnutclinics.billingService.response.ClinicBasicInfoResponse;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import com.walnutclinics.billingService.response.PatientBasicInfoResponse;
import com.walnutclinics.billingService.response.TreatmentBasicInfoResponse;
import com.walnutclinics.billingService.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/patient")
@ApiResponse(description = "Patient Controller")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(summary = "Get all Patient List",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all available Patient", content = {@Content(mediaType = "application/json"
                            , schema = @Schema(implementation = Patient.class))}),
                    @ApiResponse(responseCode = "401", description = "Either token expired or trying to access api without authentication", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Sever error, Please get in touch with development team", content = {@Content(mediaType = "application/json")})
            }
    )
    @GetMapping("/patientList")
    public List<Patient> getPatientList() {
        return patientService.getPatientList();
    }

    @Operation(summary = "Get all Matching Patient List with the given name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all available Matching Patient with the given name", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "Either token expired or trying to access api without authentication", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Sever error, Please get in touch with development team", content = {@Content(mediaType = "application/json")})
            }
    )

    @GetMapping("/matchingPatient")
    public List<PatientBasicInfoResponse> getMatchingPatient(@RequestParam String name) {
        return patientService.searchByNames(name);
    }

    @PostMapping("/save")
    public Patient savePatient(@RequestBody PatientSaveRequest patient) {
        return patientService.save(patient);
    }

    @PatchMapping("/edit")
    public Patient editPatient(@RequestBody Patient patient) {
        return patientService.update(patient);
    }

    @DeleteMapping("/delete")
    public void deletePatient(@RequestParam int id) {
        patientService.delete(id);
    }

    @GetMapping("/getMappedTreatments")
    public List<Treatment> getMappedTreatments(@RequestParam int id) {
//        return patientService.getMappedTreatments(id);
        return null;
    }

    @GetMapping("/getMappedDoctorsBasedOnPatient")
    public List<DoctorBasicInfoResponse> getMappedDoctors(@NotNull @RequestParam int id) {
        return patientService.getMappedDoctors(id);
    }

    @GetMapping("/getMappedClinics")
    public List<Clinic> getMappedClinics(@RequestParam int id) {
        return null;
    }

    @GetMapping("/getMappedClinicBasedOnPatientAndDoctor")
    public List<ClinicBasicInfoResponse> getMappedDoctorsBasedOnPatientAndClinic(@NotNull @RequestParam int patientId, @NotNull @RequestParam Long doctorId) {
        return patientService.getMappedClinicsBasedOnPatientAndDoctors(patientId, doctorId);
    }

    @GetMapping("/getMappedClinicBasedOnPatientAndDoctorAndClinic")
    public List<TreatmentBasicInfoResponse> getMappedDoctorsBasedOnPatientAndClinic(@NotNull @RequestParam int patientId, @NotNull @RequestParam Long doctorId, @NotNull @RequestParam Long clinicId) {
        return patientService.getMappedTreatmentsBasedOnPatientAndDoctorAndClinic(patientId, doctorId, clinicId);
    }

    @GetMapping("/getDetailsById")
    public Patient getById (@RequestParam int id)
    {
        return patientService.getById(id);
    }


}
