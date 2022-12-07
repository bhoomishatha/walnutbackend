package com.walnutclinics.billingService.model.Bill;


import java.io.Serializable;
import java.util.Objects;


public class BillDetailsId implements Serializable {
    int lineNumber;
    String billId;

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, billId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetailsId billDetailsId = (BillDetailsId) o;
        return billDetailsId.billId.equals(billId) && billDetailsId.lineNumber == lineNumber;
    }

}



