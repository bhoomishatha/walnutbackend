package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.clinic.Clinic;
import com.walnutclinics.billingService.response.ClinicBasicInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query("select new com.walnutclinics.billingService.response.ClinicBasicInfoResponse(c.clinicId,c.name) from Clinic c where c.name like %:name% or c.location like %:name%")
    List<ClinicBasicInfoResponse> getMatchingClinic(@Param("name") String name);


}