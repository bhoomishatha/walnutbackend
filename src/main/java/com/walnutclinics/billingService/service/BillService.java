package com.walnutclinics.billingService.service;

import com.walnutclinics.billingService.model.Bill.Bill;
import com.walnutclinics.billingService.model.Bill.BillDetails;
import com.walnutclinics.billingService.model.EmailDetails;
import com.walnutclinics.billingService.repository.*;
import com.walnutclinics.billingService.request.BillDetailRequest;
import com.walnutclinics.billingService.request.BillRequest;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BillService {

    final static String[] monthMap = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    final static String descrition = """
            Branch: %s
            Treatment: %s
            Doctor: %s
            """;
    final static SimpleDateFormat Formatter = new SimpleDateFormat("dd-MMM-YYYY");
    final String mail_Body = """
            Hi %s Parent,
            Thankyou for the payment at Walnut Child Development Clinic.
                        
            Click to download the receipt :%s            
                        
            Thanks,
            WALNUT CDC
            Phone :+918668546502
            Email :contactus@walnutclinics.com
            Website:www.walnutclinics.com           
            """;
    final String mail_subject = "Payment Receipt";
    private final String templateUrl;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PatientRepository patientRepository;
    @Value("${domain}")
    private String domain;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    BillService(@Value("${receipt.template.path}") String templatePath) throws FileNotFoundException {
        String root = System.getProperty("user.dir");
        templateUrl = root + templatePath;
    }

    private static String number_Word(int num) {
        String words = "";
        String[] unitarr = {"Zero", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
                "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
                "Eighteen", "Nineteen"};
        String[] tensarr = {"Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty",
                "Sixty", "Seventy", "Eighty", "Ninety"};
        if (num == 0) {
            return "Zero";
        }

        if (num < 0) {
            // converting the number to a string
            String numberStr = "" + num;
            // removing minus before the number
            numberStr = numberStr.substring(1);
            // add minus before the number and convert the rest of number
            return "minus " + number_Word(Integer.parseInt(numberStr));
        }
        // cconditon for divisible by 1 million
        if ((num / 1000000) > 0) {
            words += number_Word(num / 1000000) + " Million ";
            num %= 1000000;
        }
        // cconditon for divisible by 1 thousand
        if ((num / 1000) > 0) {
            words += number_Word(num / 1000) + " Thousand ";
            num %= 1000;
        }
        // cconditon for divisible by 1 hundred
        if ((num / 100) > 0) {
            words += number_Word(num / 100) + " Hundred ";
            num %= 100;
        }
        if (num > 0) {
            if (num < 20) {
                words += unitarr[num];
            } else {
                words += tensarr[num / 10];
                if ((num % 10) > 0) {
                    words += "-" + unitarr[num % 10];
                }
            }
        }
        return words;
    }

    public String getBillId(int patientId) {
        YearMonth ym1 = YearMonth.now();
        return (patientId + "") + ym1.get(ChronoField.YEAR) + monthMap[ym1.get(ChronoField.MONTH_OF_YEAR) - 1] + (billRepository.getBillSeq(patientId) + 1);
    }

    public ByteArrayOutputStream getPdf(String billId) throws IOException {
        InputStream template = new FileInputStream(new File(templateUrl));
        XWPFDocument document = new XWPFDocument(template);
        template.close();
        Bill bill = billRepository.getReferenceById(billId);
        writeHeaderDetails(document, bill);
        writeDetailsTable(document, bill);
        ByteArrayOutputStream finalPdf = new ByteArrayOutputStream();
        PdfOptions options = PdfOptions.create();
        PdfConverter.getInstance().convert(document, finalPdf, options);
        document.close();
        return finalPdf;
    }

    private void writeHeaderDetails(XWPFDocument document, Bill bill) {
        //No
        document.getTableArray(0).getRow(0).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + bill.getBillId(), 0);
        //Date
        document.getTableArray(0).getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + Formatter.format(bill.getBillDate()), 0);
        //Generate by
        document.getTableArray(0).getRow(2).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + bill.getGeneratedBy(), 0);

        //Name
        document.getTableArray(1).getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + bill.getPatient().getFirstName().trim() + " " + bill.getPatient().getMiddleName().trim() + " " + bill.getPatient().getLastName().trim(), 0);
        LocalDate dob = bill.getPatient().getDateOfBirth().toLocalDate();
        LocalDate curDate = LocalDate.now();
        Period period = Period.between(dob, curDate);
        document.getTableArray(1).getRow(2).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + period.getYears() + " years " + period.getMonths() + " months", 0);
        document.getTableArray(1).getRow(3).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + (bill.getPatient().getGender() == "M" ? "Male" : "Female"), 0);
        document.getTableArray(2).getRow(1).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + bill.getPaymentMode(), 0);
        document.getTableArray(2).getRow(2).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + bill.getPaymentId(), 0);
        document.getTableArray(2).getRow(3).getCell(1).getParagraphs().get(0).getRuns().get(0).setText(": " + Formatter.format(bill.getPaymentDate()), 0);
    }

    private void writeDetailsTable(XWPFDocument document, Bill bill) throws IOException {
        XWPFTable detailTable = document.getTableArray(3);
        detailTable.getRow(0).setRepeatHeader(true);
        XWPFTableRow row = detailTable.getRow(1);
        XWPFTableRow total_row = new XWPFTableRow((CTRow) detailTable.getRow(3).getCtRow().copy(), detailTable);

        int totalSession = 0;
        int total = 0;
        int totalDiscount = 0;
        for (BillDetails details : bill.getBillDetails()) {
            XWPFTableRow copiedRow = new XWPFTableRow((CTRow) row.getCtRow().copy(), detailTable);
            copiedRow.getCell(0).getParagraphs().get(0).getRuns().get(0).setText((details.getLineNumber()) + "", 0);
            String detail = String.format(descrition, details.getClinic().getLocation(), details.getTreatment().getName(), "Dr." + details.getDoctor().getFirstName().trim() + " " + details.getDoctor().getMiddleName().trim() + " " + details.getDoctor().getLastName().trim());
            copiedRow.getCell(1).getParagraphs().get(0).getRuns().get(0).setText(detail, 0);
            totalSession += details.getNo_of_session();
            copiedRow.getCell(2).getParagraphs().get(0).getRuns().get(0).setText(details.getNo_of_session() + "", 0);
            copiedRow.getCell(3).getParagraphs().get(0).getRuns().get(0).setText(details.getPer_session_cost() + "", 0);
            totalDiscount += (details.getDiscount() * details.getNo_of_session());
            total += details.getSub_total();
            copiedRow.getCell(4).getParagraphs().get(0).getRuns().get(0).setText(details.getPer_session_cost() * details.getNo_of_session() + "", 0);
            detailTable.addRow(copiedRow);
        }

        total_row.getCell(1).getParagraphs().get(0).getRuns().get(0).setText(totalSession + "", 0);
        total_row.getCell(3).getParagraphs().get(0).getRuns().get(0).setText(total + totalDiscount + "", 0);
        total_row.getCell(3).getParagraphs().get(1).getRuns().get(0).setText(totalDiscount + "", 0);
        total_row.getCell(3).getParagraphs().get(2).getRuns().get(0).setText((total) + "", 0);

        detailTable.addRow(total_row);
        XWPFParagraph p = document.createParagraph();
        XWPFRun newR = p.createRun();
        newR.setText("Total in Words: " + number_Word(total) + " only", 0);
        detailTable.removeRow(1);
        detailTable.removeRow(1);
        detailTable.removeRow(1);
    }

    public List<Bill> getAllPatientBills(int patientId) {
        return billRepository.getPatientBills(patientId);
    }

    public Bill save(BillRequest billRequest) {

        Bill bill = new Bill();
        bill.setBillId(billRequest.getBillId());
        bill.setBillDate(billRequest.getBillDate());

        bill.setPaymentBank(billRequest.getPaymentBank());
        bill.setPaymentId(billRequest.getPaymentId());
        bill.setPaymentDate(billRequest.getPaymentDate());
        bill.setPaymentMode(billRequest.getPaymentMode());

        bill.setGeneratedBy(billRequest.getGeneratedBy());

        bill.setPatient(patientRepository.getReferenceById(billRequest.getPatientId()));
        List<BillDetails> billDetails = new ArrayList<>();
        int total = 0;
        for (BillDetailRequest billDetailRequest : billRequest.getBillDetails()) {
            BillDetails billDetail = new BillDetails();

            billDetail.setBillId(billDetailRequest.getBillId());
            billDetail.setLineNumber(billDetailRequest.getLineNumber());
            billDetail.setNo_of_session(billDetailRequest.getNo_of_session());
            billDetail.setPer_session_cost(billDetailRequest.getPer_session_cost());
            billDetail.setDiscount(billDetailRequest.getDiscount());
            billDetail.setDiscount_reason(billDetailRequest.getDiscount_reason());
            billDetail.setSession_end_Date(billDetailRequest.getSession_start_Date());
            billDetail.setSession_end_Date(billDetailRequest.getSession_end_Date());
            billDetail.setSub_total(billDetailRequest.getSub_total());
            total += billDetail.getSub_total();
            billDetail.setClinic(clinicRepository.getReferenceById(billDetailRequest.getClinicId()));
            billDetail.setDoctor(doctorRepository.getReferenceById(billDetailRequest.getDoctorId()));
            billDetail.setTreatment(treatmentRepository.getReferenceById(billDetailRequest.getTreatmentId()));
            billDetails.add(billDetail);
        }

        bill.setBillDetails(billDetails);
        bill.setTotal(total);

//        System.out.println(bill.getPatient().getId());

        String billId = getBillId(bill.getPatient().getId());
        bill.setBillId(billId);
        for (int i = 0; i < bill.getBillDetails().size(); i++) {
            bill.getBillDetails().get(i).setBillId(billId);
        }
        bill.setRandom(Math.round((Math.random() * 10000)) + "");
        bill = billRepository.save(bill);
        (new Thread(new Runnable() {
            public void run() {
                try {
                    sendBillViaMail(billId);
                } catch (IOException e) {
                }
            }
        })).start();
        return bill;
    }

    public boolean saveBillDetails(List<BillDetails> billDetails) {
        billDetailsRepository.saveAll(billDetails);
        return true;
    }

    public boolean sendBillViaMail(String receiptId) throws IOException {
        EmailDetails emailDetails = new EmailDetails();
        Optional<Bill> bill = billRepository.findById(receiptId);
        String linkToFile = domain + "/api/v1/receipt/receiptView/" + receiptId + "/" + bill.get().getRandom();
        emailDetails.setRecipient(bill.get().getPatient().getEmail());
        emailDetails.setMsgBody(String.format(mail_Body, bill.get().getPatient().getFirstName() + "'s", linkToFile));
        emailDetails.setSubject(mail_subject);
        return emailService.sendSimpleMail(emailDetails);
    }

    public ByteArrayOutputStream downloadPdfViaLink(String receiptId, String secert) throws IOException {
        Optional<Bill> bill = billRepository.findById(receiptId);
        if (bill.get().getRandom().equals(secert))
            return getPdf(receiptId);
        else
            return null;
    }

    @Transactional
    public void delete(String billId) {

//      billRepository.deleteById(billId);
        billDetailsRepository.deleteByBillId(billId);
        billRepository.deleteByBillId(billId);

    }

    public Bill update(BillRequest billRequest) {

        Bill bill = billRepository.findById(billRequest.getBillId()).get();
        bill.setBillId(billRequest.getBillId());
        bill.setBillDate(billRequest.getBillDate());

        bill.setPaymentBank(billRequest.getPaymentBank());
        bill.setPaymentId(billRequest.getPaymentId());
        bill.setPaymentDate(billRequest.getPaymentDate());
        bill.setPaymentMode(billRequest.getPaymentMode());

        bill.setPatient(patientRepository.getReferenceById(billRequest.getPatientId()));
        List<BillDetails> billDetails = bill.getBillDetails();
        int total = 0;
        int i = 0;
        for (BillDetailRequest billDetailRequest : billRequest.getBillDetails()) {

            billDetails.get(i).setBillId(billDetailRequest.getBillId());
            billDetails.get(i).setLineNumber(billDetailRequest.getLineNumber());
            billDetails.get(i).setNo_of_session(billDetailRequest.getNo_of_session());
            billDetails.get(i).setPer_session_cost(billDetailRequest.getPer_session_cost());
            billDetails.get(i).setDiscount(billDetailRequest.getDiscount());
            billDetails.get(i).setDiscount_reason(billDetailRequest.getDiscount_reason());
            billDetails.get(i).setSession_end_Date(billDetailRequest.getSession_start_Date());
            billDetails.get(i).setSession_end_Date(billDetailRequest.getSession_end_Date());
            billDetails.get(i).setSub_total(billDetailRequest.getSub_total());
            total += billDetails.get(i).getSub_total();
            billDetails.get(i).setClinic(clinicRepository.getReferenceById(billDetailRequest.getClinicId()));
            billDetails.get(i).setDoctor(doctorRepository.getReferenceById(billDetailRequest.getDoctorId()));
            billDetails.get(i).setTreatment(treatmentRepository.getReferenceById(billDetailRequest.getTreatmentId()));
            i++;
        }

        bill.setBillDetails(billDetails);
        bill.setTotal(total);

//        System.out.println(bill.getPatient().getId());


        bill.setRandom(Math.round((Math.random() * 10000)) + "");
        bill = billRepository.save(bill);

        return bill;


    }


}
