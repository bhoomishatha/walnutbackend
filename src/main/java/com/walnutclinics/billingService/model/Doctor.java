package com.walnutclinics.billingService.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.patient.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor Id cannot be Empty")
    private Long doctorId;

    @Column
    @NotNull(message = "Doctor First Name cannot be Blank")
    private String firstName;

    @Column
    private String middleName;

    @Column
    @NotNull(message = "Doctor Last Name Cannot be Blank")
    private String lastName;

    @Column
    @NotNull(message = "Doctor Gender Cannot Be Null")
    private String gender;

    @Column
    @NotNull(message = "Doctor Status Cannot be Blank")
    private boolean status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "clinic_doctor",
            joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn(name = "clinic_id")})
    private List<Clinic> clinics;

    @ManyToMany(mappedBy = "doctors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Treatment> treatments;

    @ManyToMany(mappedBy = "doctors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Patient> patients;
}