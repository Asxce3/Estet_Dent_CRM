package org.example.test_orm.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.VisitsDTO;
import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.entity.payment.DTO.PaymentDto;
import org.example.test_orm.entity.payment.FinanceDirectory;
import org.example.test_orm.entity.payment.Payment;
import org.example.test_orm.entity.payment.PaymentType;
import org.example.test_orm.service.VisitsService;
import org.example.test_orm.service.auth.AuthService;
import org.example.test_orm.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final PaymentService paymentService;
    private final VisitsService visitsService;
    private final AuthService authService;

    @GetMapping("/patient/{id}")
    @ResponseBody
    public List<VisitsDTO> getPatientVisits(@PathVariable long id) {
        log.info("Request for getting patient visits by patientId with id : {}", id);
        return visitsService.getPatientVisitsDTO(id);
    }

    @GetMapping
    public String getAllVisits(
            @RequestParam(required = false) String inputWeek, Model model, HttpServletRequest request) {
        Doctor doctor = authService.getDoctorFromCookie(request.getCookies());
        LocalDate date = visitsService.parseOrCreateDate(inputWeek);

        model.addAttribute("daysOfWeek", List.of(DayOfWeek.values()));
        model.addAttribute("startDate", date);
        model.addAttribute("endDate", date.plusDays(6));
        model.addAttribute("visits", visitsService.getVisits(date, doctor));
        return "visits";
    }

    @GetMapping("/create")
    public String createPageVisit(Model model) {
        model.addAttribute("visit", new VisitsDTO());
        return "create_visit";
    }

    @GetMapping("/update/{id}")
    public String updatePageVisit(@PathVariable long id,
                                  Model model) {

        Visits visits = visitsService.getVisit(id);
        model.addAttribute("patient", visits.getPatient());
        model.addAttribute("medHistory", visits.getMedHistory());
        model.addAttribute("visit", visitsService.getVisitDTO(visits));
        return "update_visits";
    }

    @PostMapping
    public String createVisit(@ModelAttribute VisitsDTO visitsDTO) {
        System.out.println("create");
        System.out.println(visitsDTO);
        visitsService.createVisits(visitsDTO);
        return "redirect:/visits";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateTimeVisit(@RequestBody VisitsDTO visitsDTO) {
        log.info("Request for update time on Visit with: {}", visitsDTO);
        visitsService.updateVisits(visitsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteVisit(@PathVariable long id) {
        visitsService.deleteVisits(id);
        return "redirect:/visits";
    }

    @GetMapping("/{id}/cancel")
    public String canselVisit(@PathVariable long id) {
        visitsService.canselVisit(id);
        return "redirect:/visits";
    }

//    Payment
    @GetMapping("/payment/finance")
    @ResponseBody
    public List<FinanceDirectory> getDirectories() {
        log.info("Request for getting finance directories ");
        return paymentService.getDirectories();
    }

    @GetMapping("/payment/types")
    @ResponseBody
    public List<PaymentType> getPaymentTypes() {
        log.info("Request for getting payment types");
        return paymentService.getPaymentTypes();
    }

    @GetMapping("/payment/patient/{id}")
    @ResponseBody
    public List<PaymentDto> getPayments(@PathVariable long id) {
        log.info("Request for getting patient payments with id: {}", id);
        return paymentService.getPaymentsByPatientId(id);
    }

    @PostMapping("/payment")
    @ResponseBody
    public ResponseEntity<?> createPayment(@RequestBody PaymentDto payment) {
        log.info("Request for create payment {}", payment);
        paymentService.createPayment(payment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/payment")
    @ResponseBody
    public ResponseEntity<?> updatePayment(@RequestBody PaymentDto payment) {
        log.info("Request for update payment {}", payment);
        paymentService.updatePayment(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
