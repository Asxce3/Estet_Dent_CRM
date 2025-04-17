package org.example.test_orm.repository;

import org.example.test_orm.entity.MedCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedCardRepository extends JpaRepository<MedCard, Long> {
    List<MedCard> findMedCardsByVisitsPatientID(long patientID);
}
