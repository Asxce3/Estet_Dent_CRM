package org.example.test_orm.entity.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FinanceValuePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FinanceValue financeValue;

    @ManyToOne
    private Payment payment;

    private Integer count;
}
