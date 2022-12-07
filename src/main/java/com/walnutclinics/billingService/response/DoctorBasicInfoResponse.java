package com.walnutclinics.billingService.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorBasicInfoResponse {

    long doctor_id;
    String fullName;
}
