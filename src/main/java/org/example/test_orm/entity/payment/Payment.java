package org.example.test_orm.entity.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.test_orm.entity.Visits;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal receiptOfMoney;  // Оплачено

    private BigDecimal debt;    // Долг

    @OneToOne
    private PaymentType paymentType;

    @OneToOne
    private Visits visits;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinanceValuePayment> financeValuePayments = new ArrayList<>();


    public void addFinanceValue(FinanceValuePayment financeValuePayment) {
        this.financeValuePayments.add(financeValuePayment);
        financeValuePayment.setPayment(this);
    }

    public void removeFinanceValue(FinanceValuePayment financeValuePayment) {
        this.financeValuePayments.remove(financeValuePayment);
        financeValuePayment.setPayment(null);
    }

}
