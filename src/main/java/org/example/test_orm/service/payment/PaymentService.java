package org.example.test_orm.service.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.entity.payment.*;
import org.example.test_orm.entity.payment.DTO.FinanceValuePaymentDto;
import org.example.test_orm.entity.payment.DTO.PaymentDto;
import org.example.test_orm.repository.VisitsRepository;
import org.example.test_orm.repository.payment.FinanceDirectoryRepository;
import org.example.test_orm.repository.payment.FinanceValueRepository;
import org.example.test_orm.repository.payment.PaymentRepository;
import org.example.test_orm.repository.payment.PaymentTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final FinanceValueRepository financeValueRepository;
    private final FinanceDirectoryRepository financeDirectoryRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final VisitsRepository visitsRepository;

    public void createPayment(PaymentDto dto) {
        try {
            Payment payment = mappingPayment(dto);
            paymentRepository.save(payment);
            log.info("Payment success creating");
        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updatePayment(PaymentDto dto) {
        try {
            Payment payment = paymentRepository.findById(dto.getId())
                    .orElseThrow(()-> new RuntimeException("Object payment with doesn't exist"));
            BigDecimal newReceiptOfMoney = payment.getReceiptOfMoney().add(dto.getReceiptOfMoney());

            BigDecimal newDebt = payment.getDebt().subtract(dto.getReceiptOfMoney());

            payment.setDebt(newDebt);
            payment.setReceiptOfMoney(newReceiptOfMoney);

            paymentRepository.save(payment);
            log.info("Payment success update");
        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PaymentDto> getPaymentsByPatientId(long id) {
        List<Payment> payments = paymentRepository.getPaymentsByVisitsPatientID(id);
        return payments.stream().map(this::mappingPaymentDto).toList();
    }

    private PaymentDto mappingPaymentDto(Payment payment) {
        try {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(payment.getId());
            paymentDto.setDebt(payment.getDebt());
            paymentDto.setReceiptOfMoney(payment.getReceiptOfMoney());
            paymentDto.setVisitsId(payment.getVisits().getID());
            paymentDto.setFinanceValuePayments(
                    payment.getFinanceValuePayments().stream()
                    .map(this::mappingFinanceValuePaymentDto).toList()
            );
            paymentDto.setPaymentTypeId(payment.getPaymentType().getId());
            return paymentDto;

        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private FinanceValuePaymentDto mappingFinanceValuePaymentDto(FinanceValuePayment financeValuePayment) {
        try {
            FinanceValuePaymentDto dto = new FinanceValuePaymentDto();
            dto.setId(financeValuePayment.getId());
            dto.setCount(financeValuePayment.getCount());
            dto.setFinanceValueId(financeValuePayment.getFinanceValue().getId());
            return dto;

        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
    private Payment mappingPayment(PaymentDto dto) {
        try {
            Payment payment = new Payment();
            payment.setReceiptOfMoney(dto.getReceiptOfMoney());
            payment.setDebt(dto.getDebt());
            log.info("Try to find PaymentType with id {}", dto.getPaymentTypeId());
            PaymentType paymentType = paymentTypeRepository.findById(dto.getPaymentTypeId())
                    .orElseThrow(() -> new RuntimeException(String.format("PaymentType with id: %s doesn't exist", dto.getPaymentTypeId())));
            payment.setPaymentType(paymentType);
            log.info("Try to find Visits with id {}", dto.getPaymentTypeId());
            Visits visits = visitsRepository.findById(dto.getVisitsId())
                    .orElseThrow(() -> new RuntimeException(String.format("Visit with id: %s doesn't exist", dto.getVisitsId())));
            payment.setVisits(visits);
            log.info("Iterating list FinanceValuePaymentDto for mapping");
            for(FinanceValuePaymentDto value: dto.getFinanceValuePayments()) {

                payment.addFinanceValue(mappingFinanceValuePayment(value));
                log.info("Value with id {} successful added to list", value.getId());
            }
            return payment;

        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private FinanceValuePayment mappingFinanceValuePayment(FinanceValuePaymentDto dto) {
        try {
            FinanceValuePayment financeValuePayment = new FinanceValuePayment();
            financeValuePayment.setCount(dto.getCount());
            log.info("Try to find FinanceValue with id {}", dto.getFinanceValueId());
            FinanceValue financeValue = financeValueRepository.findById(dto.getFinanceValueId())
                    .orElseThrow(() -> new RuntimeException("FinanceValue not present with id: " + dto.getFinanceValueId()));
            financeValuePayment.setFinanceValue(financeValue);
            return financeValuePayment;

        }   catch (Exception e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<FinanceDirectory> getDirectories() {
        return financeDirectoryRepository.findAll();
    }

    public List<PaymentType> getPaymentTypes() {
        return paymentTypeRepository.findAll();
    }
}
