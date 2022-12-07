package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.Doctor;
import com.walnutclinics.billingService.response.DoctorBasicInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("select new com.walnutclinics.billingService.response.DoctorBasicInfoResponse(d.doctorId,concat('Dr.',trim(d.firstName),' ',trim(d.middleName),' ',trim(d.lastName))) from Doctor d where d.firstName like %:name% or d.middleName like %:name% or d.lastName like %:name%")
    List<DoctorBasicInfoResponse> getMatchingPatients(@Param("name") String name);

}