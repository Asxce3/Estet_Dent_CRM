package org.example.test_orm.entity.payment.DTO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PaymentDto {
    private Long id;

    private BigDecimal receiptOfMoney;  // Оплачено

    private BigDecimal debt;    // Долг

    private Long paymentTypeId;

    private Long visitsId;

    private List<FinanceValuePaymentDto> financeValuePayments = new ArrayList<>();
}
