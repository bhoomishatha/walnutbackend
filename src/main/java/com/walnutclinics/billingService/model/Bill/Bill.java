package com.walnutclinics.billingService.model.Bill;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.walnutclinics.billingService.model.patient.Patient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Table(name = "bill")
@Getter
@Setter
@Entity
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@DynamicUpdate
public class Bill implements Serializable {
    @Id
    @Column(name = "bill_id", nullable = false)
    private String billId;

    @Column(name = "bill_date")
    @NotNull(message = "bill Date is Mandatory")
    private Date billDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    @NotNull(message = "Patient id is mandatory")
    private Patient patient;

    @Transient
    private int patientId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "payment_date")

    private Date paymentDate;
    @PastOrPresent(message = "Date cannot be Future")
    @NotNull(message = "Payment Date Cannot be Null")

    @Column(name = "payment_bank")
    private String paymentBank;

    @Column(name = "random")
    @NotNull
    @JsonIgnore
    private String random;

    private int total;

    @Column(name = "generate_by")
    @NotNull(message = "Cannot identify the user")
    private String generatedBy;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "billId")
    @NotFound(
            action = NotFoundAction.IGNORE)
    @NotNull(message = "No Bill details added")
    private List<BillDetails> billDetails;

    public int getPatientId() {
        return patient.getId();
    }

}
