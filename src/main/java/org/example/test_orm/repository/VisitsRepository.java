package org.example.test_orm.repository;

import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.StatusVisit;
import org.example.test_orm.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Long> {
    List<Visits> findByPatientDoctorAndDateOfVisitBetween(Doctor doctor, LocalDate startWeek, LocalDate finishWeek);
    List<Visits> findByPatientDoctorAndDateOfVisitBetweenAndStatusVisit(Doctor doctor, LocalDate startWeek, LocalDate finishWeek, StatusVisit statusVisit);

}
