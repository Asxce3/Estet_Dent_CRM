package org.example.test_orm.repository.teeth;

import org.example.test_orm.entity.ToothCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToothConditionRepository extends JpaRepository<ToothCondition, Long> {
    Optional<ToothCondition> getToothConditionByCondition(String name);
}
