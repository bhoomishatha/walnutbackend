package com.walnutclinics.billingService.controller;

import com.walnutclinics.billingService.model.Bill.Bill;
import com.walnutclinics.billingService.model.Bill.BillDetails;
import com.walnutclinics.billingService.request.BillRequest;
import com.walnutclinics.billingService.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/receipt")
public class ReceiptController {

    @Autowired
    private BillService billService;

    @GetMapping("/getReceiptId/{patientId}")
    public String billID(@PathVariable int patientId) {
        return billService.getBillId(patientId);
    }

    @GetMapping("/downloadReceipt/{receiptId}")
    public void DownloadBill(@PathVariable String receiptId, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + receiptId + ".pdf");
        ServletOutputStream out = response.getOutputStream();
        billService.getPdf(receiptId).writeTo(response.getOutputStream());
        out.close();
    }

    @GetMapping("/allReceipt/{patientId}")
    public List<Bill> getAllBills(@PathVariable int patientId) {
        return billService.getAllPatientBills(patientId);
    }

    @PostMapping("/saveReceipt")
    public Bill billSave(@RequestBody BillRequest billRequest) {
        return billService.save(billRequest);
    }

    @PostMapping("/saveReceiptDetails")
    public boolean billDetailsSave(@RequestBody List<BillDetails> billDetails) {
        return billService.saveBillDetails(billDetails);
    }

    @GetMapping("/sendCopyViaMail/{receiptId}")
    public boolean DownloadBillViaLink(@PathVariable String receiptId) throws IOException {
        return billService.sendBillViaMail(receiptId);
    }

    @GetMapping("/receiptView/{receiptId}/{password}")
    public void DownloadBillViaLink(@PathVariable String receiptId, @PathVariable String password, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + receiptId + ".pdf");
            ServletOutputStream out = response.getOutputStream();
            billService.downloadPdfViaLink(receiptId, password).writeTo(response.getOutputStream());
            out.close();
        } catch (NullPointerException e) {
            response.setStatus(404);
        }
    }

    @DeleteMapping("/delete")
    public void deleteBill(@RequestParam String billId) {
        billService.delete(billId);
    }

    @PatchMapping("/update")
    public Bill updateBill(@RequestBody BillRequest bill) {
        return billService.update(bill);
    }


}
