package org.example.test_orm.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinanceMaterialDTO {
    private String name;
    private BigDecimal price;
    private int count;
    private BigDecimal totalPrice;
}
