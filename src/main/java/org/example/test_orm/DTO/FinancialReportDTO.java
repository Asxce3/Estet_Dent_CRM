package org.example.test_orm.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class FinancialReportDTO {
    private LocalDate date;
    private List<FinanceWorkDTO> financeWorkDTOList = new ArrayList<>();
    private List<FinanceMaterialDTO> financeMaterialDTOList = new ArrayList<>();
    private BigDecimal debt;
    private BigDecimal receiptOfMoney;

}
