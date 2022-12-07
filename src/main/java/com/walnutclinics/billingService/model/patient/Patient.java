package com.walnutclinics.billingService.model.patient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.model.Treatment;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "patient")
@Data
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    @NotNull(message = "Patient Id cannot be Null")
    private Integer id;

    @Column(name = "first_name")
    @NotNull(message = "Patient First Name cannot be Null")
    private String firstName;


    @Column(name = "middle_name")
    private String middleName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_number")
    private String fathersNumber;

    @Column(name = "mother_number")
    private String motherNumber;

    @Column(name = "whatsapp_Number")
    private Long whatsappNumber;

    @Column(name = "address")
//    @NotNull(message = "Address cannot cannot be Null")
    private String address;

    @Column(name = "state")
//    @NotNull(message = "State cannot be Null")
    private String state;

    @Column(name = "city")
//    @NotNull(message = "City Cannot be Null")
    private String city;

    @Column(name = "pincode")
//    @NotNull(message = "Pincode cannot be Null")
    private String pincode;

    @Column(name = "date_of_birth")
    @NotNull(message = "Patient Date of Birth cannot be Null")
    @Past(message = "Date of Birth should be in Past")
    private Date dateOfBirth;

    @Column(name = "country")
//    @NotNull(message = "Patient Country of Residence is Mandatory")
    private String country;

    @Column(name = "gender")
    @NotNull(message = "Patient Gender cannot be Null")
    private String gender;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_patient",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")})
    @JsonIgnore
    private List<Doctor> doctors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_patient",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "clinic_id")})
    @JsonIgnore
    private List<Clinic> clinics;

    @ManyToMany(mappedBy = "patients", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Treatment> treatments;

}