package org.example.test_orm.repository;

import org.example.test_orm.entity.MedHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedHistoryRepository extends JpaRepository<MedHistory, Long> {
}
