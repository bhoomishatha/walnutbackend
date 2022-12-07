package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.Bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, String> {
    @Query("select count(*) from Bill b where b.patient.id = :#{#patientId}")
    int getBillSeq(@Param("patientId") int patientId);

    @Query("select b from Bill b where b.patient.id = :#{#patientId}")
    List<Bill> getPatientBills(@Param("patientId") int patientId);

    @Modifying
    @Query("delete from Bill b where b.billId = :#{#billId}")
    void deleteByBillId(@Param("billId") String billId);

}