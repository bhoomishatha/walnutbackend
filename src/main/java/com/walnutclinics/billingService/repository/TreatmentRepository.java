package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.Treatment;
import com.walnutclinics.billingService.response.TreatmentBasicInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    @Query("select new com.walnutclinics.billingService.response.TreatmentBasicInfoResponse(t.treatmentId,t.name) from Treatment t where t.name like %:name%")
    List<TreatmentBasicInfoResponse> getMatchingTreatment(@Param("name") String name);

}