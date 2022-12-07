package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.patient.Patient;
import com.walnutclinics.billingService.response.PatientBasicInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("select new com.walnutclinics.billingService.response.PatientBasicInfoResponse(p.id,concat(trim(p.firstName),' ',trim(p.middleName),' ',trim(p.lastName))) from Patient p where p.firstName like %:name% or p.middleName like %:name% or p.lastName like %:name%")
    List<PatientBasicInfoResponse> getMatchingPatients(@Param("name") String name);
}