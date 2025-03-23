package org.example.test_orm.repository;

import org.example.test_orm.entity.MedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedHistoryRepository extends JpaRepository<MedHistory, Long> {
    List<MedHistory> getMedHistoriesByPatient_ID(long id);
}
