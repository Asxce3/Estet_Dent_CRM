package org.example.test_orm.repository.payment;

import org.example.test_orm.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getPaymentsByVisitsPatientID(long id);
}
