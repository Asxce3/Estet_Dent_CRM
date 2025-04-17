package org.example.test_orm.repository;

import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findPatientsByDoctor(Doctor doctor);
    List<Patient> findByNameStartingWith(String name);
    List<Patient> findByNameStartingWithAndDoctor(String name, Doctor doctor);
}
