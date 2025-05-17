package org.example.test_orm.entity.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class FinanceDirectory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    private String name;

    @OneToMany(mappedBy = "financeDirectory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinanceValue> financeValueList = new ArrayList<>();

    public void addFinanceValue(FinanceValue financeValue) {
        this.financeValueList.add(financeValue);
        financeValue.setFinanceDirectory(this);
    }

    public void removeFinanceValue(FinanceValue financeValue) {
        this.financeValueList.remove(financeValue);
        financeValue.setFinanceDirectory(null);
    }
}
