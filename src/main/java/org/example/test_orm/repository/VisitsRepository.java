package org.example.test_orm.repository;

import org.example.test_orm.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Long> {

}
