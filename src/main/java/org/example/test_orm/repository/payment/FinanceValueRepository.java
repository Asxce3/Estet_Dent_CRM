package org.example.test_orm.repository.payment;

import org.example.test_orm.entity.payment.FinanceValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceValueRepository extends JpaRepository<FinanceValue, Long> {
}
