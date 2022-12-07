package com.walnutclinics.billingService.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class BillDetailRequest {

    private int lineNumber;

    private String billId;

    private long clinicId;

    private long doctorId;

    private long treatmentId;

    private int no_of_session;

    private int per_session_cost;

    private int discount;

    private String discount_reason;

    private int sub_total;

    private Date session_start_Date;

    private Date session_end_Date;
}
