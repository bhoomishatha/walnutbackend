package com.walnutclinics.billingService.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.patient.Patient;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "treatment")

public class Treatment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "treatment_id", nullable = false)
    private Long treatmentId;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private boolean status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_treatment",
            joinColumns = {@JoinColumn(name = "treatment_id")},
            inverseJoinColumns = {@JoinColumn(name = "clinic_id")})
    @JsonIgnore
    private List<Clinic> clinics;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_treatment",
            joinColumns = {@JoinColumn(name = "treatment_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")})
    @JsonIgnore
    private List<Doctor> doctors;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "patient_treatment",
            joinColumns = {@JoinColumn(name = "treatment_id")},
            inverseJoinColumns = {@JoinColumn(name = "patient_id")})
    @JsonIgnore
    private List<Patient> patients;

}