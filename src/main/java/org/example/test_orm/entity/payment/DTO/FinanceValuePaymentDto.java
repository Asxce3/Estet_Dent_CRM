package org.example.test_orm.entity.payment.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class FinanceValuePaymentDto {

    private Long id;

    private Long financeValueId;

    private Integer count;
}
