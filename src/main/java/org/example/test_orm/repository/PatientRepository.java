package org.example.test_orm.repository;

import org.example.test_orm.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<List<Patient>> findPatientsByName(String name);
    List<Patient> findByNameStartingWith(String name);
}
