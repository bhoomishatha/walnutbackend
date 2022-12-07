package com.walnutclinics.billingService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PatientBasicInfoResponse {

    private int id;
    private String fullName;
}
