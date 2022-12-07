package com.walnutclinics.billingService.request;

import lombok.Data;

import java.sql.Date;

@Data
public class PatientSaveRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    private Date dateOfBirth;

    private String emailId;

    private String gender;

}
