package com.walnutclinics.billingService.controller;


import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.response.ClinicBasicInfoResponse;
import com.walnutclinics.billingService.service.ClinicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/clinic")
@ApiResponse(description = "Clinic Controller")
public class ClinicController {

    @Autowired
    ClinicService clinicService;

    @GetMapping("/getClinicList")
    public List<Clinic> getClinics() {
        return clinicService.getAllClinic();
    }


    @Operation(summary = "To save Clinic",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Save Clinic", content = {@Content(mediaType = "application/json"
                            , schema = @Schema(implementation = Clinic.class))}),
                    @ApiResponse(responseCode = "401", description = "Either token expired or trying to access api without authentication", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500", description = "Sever error, Please get in touch with development team", content = {@Content(mediaType = "application/json")})
            }
    )
    @PostMapping(value = "/save" )
    public Clinic saveClinic(@RequestBody Clinic clinic) {
        return clinicService.saveClinic(clinic);
    }

    @PatchMapping("/update")
    public Clinic editClinic(@RequestBody Clinic clinic) {
        return clinicService.updateClinic(clinic);
    }

    @DeleteMapping("/delete")
    public void deleteClinic(@RequestParam long clinicId) {
        clinicService.deleteClinic(clinicId);
    }

    @GetMapping("/getMatchingClinic")
    public List<ClinicBasicInfoResponse> matchingClinic(@RequestParam String clinicName) {
        return clinicService.getMatchingClinic(clinicName);
    }

    @GetMapping
    public Clinic getClinic(@RequestParam long clinicId) {
        return clinicService.viewClinic(clinicId);
    }


}
