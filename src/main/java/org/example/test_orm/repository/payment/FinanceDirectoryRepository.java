package org.example.test_orm.repository.payment;

import org.example.test_orm.entity.payment.FinanceDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceDirectoryRepository  extends JpaRepository<FinanceDirectory, Long> {
}
