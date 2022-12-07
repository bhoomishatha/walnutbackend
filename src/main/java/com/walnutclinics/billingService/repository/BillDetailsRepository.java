package com.walnutclinics.billingService.repository;

import com.walnutclinics.billingService.model.Bill.BillDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BillDetailsRepository extends JpaRepository<BillDetails, Long> {

    @Modifying
    @Query("delete from BillDetails b where b.billId = :#{#billId}")
    void deleteByBillId(String billId);

}