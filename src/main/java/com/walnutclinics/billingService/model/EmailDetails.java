package com.walnutclinics.billingService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;


@Data
@AllArgsConstructor
@NoArgsConstructor


public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private File attachment;

}