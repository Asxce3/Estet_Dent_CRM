package org.example.test_orm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.entity.payment.DTO.PaymentDto;
import org.example.test_orm.entity.payment.FinanceDirectory;
import org.example.test_orm.entity.payment.PaymentType;
import org.example.test_orm.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/finance")
    @ResponseBody
    public List<FinanceDirectory> getDirectories() {
        return paymentService.getDirectories();
    }

    @GetMapping("/types")
    @ResponseBody
    public List<PaymentType> getPaymentTypes() {
        return paymentService.getPaymentTypes();
    }


    @PostMapping()
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody PaymentDto payment) {
        log.info("Request for create payment {}", payment);
        paymentService.createPayment(payment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
