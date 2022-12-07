package com.walnutclinics.billingService.model.clinic;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.model.patient.Patient;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "clinic")
public class Clinic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clinic_id", nullable = false)
    @NotNull(message = "Clinic id cannot be Null")
    private Long clinicId;
    @Column
    @NotNull(message = "Clinic Name cannot be Null")
    private String name;
    @Column
    @NotNull(message = "Clinic Address Cannot be Null")
    private String address;

    @Column
    @NotNull(message = "Clinic Country Cannot be Blank")
    private String country;

    @Column
    @NotNull(message = "Clinic State Cannot be Blank")
    private String state;

    @Column
    @NotNull(message = "Clinic City cannot be Blank")
    private String city;

    @Column
    @NotNull(message = "Clinic Pin Code cannot be Blank")
    private String pincode;

    @Column
    @NotNull(message = "Clinic Location Cannot be Null")
    public String location;

    @Column
    @NotNull(message = "Message cannot be Blank")
    private boolean status;

    @ManyToMany(mappedBy = "clinics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Treatment> treatments;

    @ManyToMany(mappedBy = "clinics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Doctor> doctors;

    @ManyToMany(mappedBy = "clinics", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Patient> patients;

}