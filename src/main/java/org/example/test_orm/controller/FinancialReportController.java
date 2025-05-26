package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.FinanceMaterialDTO;
import org.example.test_orm.DTO.FinanceWorkDTO;
import org.example.test_orm.DTO.FinancialReportDTO;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.entity.MedCardMaterial;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.entity.payment.FinanceValuePayment;
import org.example.test_orm.entity.payment.Payment;
import org.example.test_orm.repository.VisitsRepository;
import org.example.test_orm.repository.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("finance")
@RequiredArgsConstructor
public class FinancialReportController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("/patient/{id}")
    @ResponseBody
    public List<FinancialReportDTO> getFinancialReport(@PathVariable long id,
                                   @RequestParam(required = false) String start,
                                   @RequestParam(required = false) String finish) {
        System.out.println(start);
        System.out.println(finish);

        LocalDate dateStart;
        LocalDate dateFinish;
        if(start == null || finish == null) {
            dateStart = LocalDate.ofYearDay(2020, 1);
            dateFinish = LocalDate.now();
        }   else {
            dateStart = LocalDate.parse(start);
            dateFinish = LocalDate.parse(finish);
        }

        log.info("Request for getting finance report patient with id: {}", id);

        List<Payment> patientPayments = paymentRepository.findByVisitDateBetweenAndPatientId
                (id, dateStart, dateFinish);

        List<FinancialReportDTO> lst = new ArrayList<>();
        for(Payment payment: patientPayments) {
            FinancialReportDTO financialReportDTO = new FinancialReportDTO();
            financialReportDTO.setDate(payment.getVisits().getDateOfVisit());

            List<FinanceWorkDTO> financeWorkDTOList = payment.getFinanceValuePayments()
                    .stream().map(this::mapping1).toList();

            financialReportDTO.setFinanceWorkDTOList(financeWorkDTOList);

            List<MedCard> medCards = payment.getVisits().getMedCards();
            List<FinanceMaterialDTO> financeMaterialDTOList = new ArrayList<>();
            if (medCards.size() > 0) {
                financeMaterialDTOList = medCards.get(0)
                        .getMedCardMaterials().stream().map(this::mapping2).toList();
            }

            financialReportDTO.setFinanceMaterialDTOList(financeMaterialDTOList);
            financialReportDTO.setDebt(payment.getDebt());
            financialReportDTO.setReceiptOfMoney(payment.getReceiptOfMoney());

            lst.add(financialReportDTO);
        }
        return lst;
    }

    private FinanceWorkDTO mapping1(FinanceValuePayment financeValuePayment) {
        FinanceWorkDTO financeWorkDTO = new FinanceWorkDTO();
        financeWorkDTO.setName(financeValuePayment.getFinanceValue().getName());
        financeWorkDTO.setPrice(financeValuePayment.getFinanceValue().getPrice());
        financeWorkDTO.setCount(financeValuePayment.getCount());

        BigDecimal price = financeValuePayment.getFinanceValue().getPrice();
        int count = financeValuePayment.getCount();
        int i = 0;
        BigDecimal totalPrice = price;
        while (i < count - 1) {
            totalPrice = totalPrice.add(price);
            i++;
        }
        financeWorkDTO.setTotalPrice(totalPrice);

        return financeWorkDTO;
    }

    private FinanceMaterialDTO mapping2(MedCardMaterial medCardMaterial) {
        FinanceMaterialDTO financeMaterialDTO = new FinanceMaterialDTO();
        financeMaterialDTO.setName(medCardMaterial.getMaterial().getName());
        financeMaterialDTO.setPrice(medCardMaterial.getMaterial().getPrice());
        financeMaterialDTO.setCount(medCardMaterial.getQuantity());

        financeMaterialDTO.setName(medCardMaterial.getMaterial().getName());

        BigDecimal price = medCardMaterial.getMaterial().getPrice();
        int count = medCardMaterial.getQuantity();
        int i = 0;
        BigDecimal totalPrice = price;
        while (i < count - 1) {
            totalPrice = totalPrice.add(price);
            i++;
        }
        financeMaterialDTO.setTotalPrice(totalPrice);

        return financeMaterialDTO;
    }

    @GetMapping("/clinic")
    @ResponseBody
    public List<FinancialReportDTO> get(@RequestParam(required = false) String start,
                    @RequestParam(required = false) String finish) {
        LocalDate dateStart;
        LocalDate dateFinish;
        if(start == null || finish == null) {
            dateStart = LocalDate.ofYearDay(2025, 1);
            dateFinish = LocalDate.now();
        }   else {
            dateStart = LocalDate.parse(start);
            dateFinish = LocalDate.parse(finish);
        }

        log.info("Request for getting finance report patient with id:");

        List<Payment> patientPayments = paymentRepository.findByVisitDateBetween(dateStart, dateFinish);

        List<FinancialReportDTO> lst = new ArrayList<>();
        for(Payment payment: patientPayments) {
            FinancialReportDTO financialReportDTO = new FinancialReportDTO();
            financialReportDTO.setDate(payment.getVisits().getDateOfVisit());

            List<FinanceWorkDTO> financeWorkDTOList = payment.getFinanceValuePayments()
                    .stream().map(this::mapping1).toList();

            financialReportDTO.setFinanceWorkDTOList(financeWorkDTOList);

            List<MedCard> medCards = payment.getVisits().getMedCards();
            List<FinanceMaterialDTO> financeMaterialDTOList = new ArrayList<>();
            if (medCards.size() > 0) {
                financeMaterialDTOList = medCards.get(0)
                        .getMedCardMaterials().stream().map(this::mapping2).toList();
            }

            financialReportDTO.setFinanceMaterialDTOList(financeMaterialDTOList);
            financialReportDTO.setDebt(payment.getDebt());
            financialReportDTO.setReceiptOfMoney(payment.getReceiptOfMoney());

            lst.add(financialReportDTO);
        }
        return lst;
    }

    @GetMapping("/page/clinic")
    public String getPage(@RequestParam(required = false) String start,
                          @RequestParam(required = false) String finish, Model model) {
        LocalDate dateStart;
        LocalDate dateFinish;
        if(start == null || finish == null) {
            dateStart = LocalDate.ofYearDay(2020, 1);
            dateFinish = LocalDate.now();
        }   else {
            dateStart = LocalDate.parse(start);
            dateFinish = LocalDate.parse(finish);
        }

        List<Payment> patientPayments = paymentRepository.findByVisitDateBetween(dateStart, dateFinish);

        List<FinancialReportDTO> lst = new ArrayList<>();
        for(Payment payment: patientPayments) {
            FinancialReportDTO financialReportDTO = new FinancialReportDTO();
            financialReportDTO.setDate(payment.getVisits().getDateOfVisit());

            List<FinanceWorkDTO> financeWorkDTOList = payment.getFinanceValuePayments()
                    .stream().map(this::mapping1).toList();

            financialReportDTO.setFinanceWorkDTOList(financeWorkDTOList);

            List<MedCard> medCards = payment.getVisits().getMedCards();
            List<FinanceMaterialDTO> financeMaterialDTOList = new ArrayList<>();
            if (medCards.size() > 0) {
                financeMaterialDTOList = medCards.get(0)
                        .getMedCardMaterials().stream().map(this::mapping2).toList();
            }

            financialReportDTO.setFinanceMaterialDTOList(financeMaterialDTOList);
            financialReportDTO.setDebt(payment.getDebt());
            financialReportDTO.setReceiptOfMoney(payment.getReceiptOfMoney());

            lst.add(financialReportDTO);
        }
        model.addAttribute("FinancialReportList", lst);
        return "finance/FinanceReport";

    }

    @GetMapping("/document/clinic")
    public String getDocumentClinic() {

        return "finance/FinanceReportDocumentClinic";
    }

    @GetMapping("/document/patient/{id}")
    public String getDocumentPatient(@PathVariable long id, Model model) {
        List<Payment> payments =  paymentRepository.getPaymentsByVisitsPatientID(id);
        if(payments.size() > 0) {
            Patient patient = payments.get(0).getVisits().getPatient();
            model.addAttribute("patient", patient);
        }   else {
            model.addAttribute("error_message", "У пациента нет истории оплаты");
        }
        return "finance/FinanceReportDocumentPatient";
    }



}
