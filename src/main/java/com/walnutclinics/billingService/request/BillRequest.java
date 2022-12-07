package com.walnutclinics.billingService.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class BillRequest {

    private String billId;

    private Date billDate;

    private int patientId;

    private String paymentId;

    private String paymentMode;

    private Date paymentDate;

    private String paymentBank;

    private String generatedBy;

    private List<BillDetailRequest> billDetails;

}
