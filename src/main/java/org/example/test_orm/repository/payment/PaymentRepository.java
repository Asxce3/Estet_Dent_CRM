package org.example.test_orm.repository.payment;

import org.example.test_orm.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getPaymentsByVisitsPatientID(long id);
    Optional<Payment> getPaymentByVisitsPatientID(long id);

    @Query("""
            SELECT p FROM Payment p
            WHERE p.visits.dateOfVisit BETWEEN :startDate AND :endDate
            AND p.visits.patient.ID = :patientId
""")
    List<Payment> findByVisitDateBetweenAndPatientId(
            @Param("patientId") long id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
            SELECT p FROM Payment p
            WHERE p.visits.dateOfVisit BETWEEN :startDate AND :endDate
""")
    List<Payment> findByVisitDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
