package com.walnutclinics.billingService.model.Bill;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.model.Treatment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Table(name = "bill_details")
@Entity
@IdClass(BillDetailsId.class)
@Getter
@Setter
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class BillDetails implements Serializable {

    @Id
    @NotNull(message = "Line number cannot be null")
    private int lineNumber;

    @Id
    @NotNull(message = "Bill id is mandatory")
    private String billId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id")
    @JsonIgnore
    @NotNull(message = "Details not mapped to Clinic")
    private Clinic clinic;

    @Transient
    private long clinicId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    @NotNull(message = "Details not mapped to Doctor")
    @JsonIgnore
    private Doctor doctor;

    @Transient
    private long doctorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treatment_id")
    @NotNull(message = "Details not mapped to Treatment")
    @JsonIgnore
    private Treatment treatment;

    @Transient
    private long treatmentId;

    @Column
    @NotNull(message = "No of treatment cannot be null")
    private int no_of_session;

    @Column
    @NotNull(message = "Enter a per session cost")
    @Min(value = 100, message = "Per session cost should be minimum 100")
    @Max(value = 5000, message = "Per Session cost can be maximum 5000")
    private int per_session_cost;

    @Column
    private int discount;

    @Column
    private String discount_reason;

    @Column
    @NotNull(message = "Sub total cannot be Null")
    @Min(message = "Value cannot be less 100", value = 100)
    private int sub_total;

    @Column
    @NotNull(message = "Session Start Date is Mandatory")
    private Date session_start_Date;

    @Column
    @NotNull(message = "Session End Date is Mandatory")
    private Date session_end_Date;

    public long getTreatmentId()
    {
        return treatment.getTreatmentId();
    }

    public long getDoctorId()
    {
        return doctor.getDoctorId();
    }

    public long getClinicId()
    {
        return clinic.getClinicId();
    }

}
