package org.example.test_orm.repository;

import org.example.test_orm.entity.Teeth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeethRepository extends JpaRepository<Teeth, Long> {

}
