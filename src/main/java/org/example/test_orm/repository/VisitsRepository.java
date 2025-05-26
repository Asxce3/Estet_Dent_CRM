package org.example.test_orm.repository;

import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.StatusVisit;
import org.example.test_orm.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Long> {
    List<Visits> findVisitsByPatientID(long patient_id);
    List<Visits> findByPatientDoctorAndDateOfVisitBetween(Doctor doctor, LocalDate startWeek, LocalDate finishWeek);
    List<Visits> findByPatientDoctorAndDateOfVisitBetweenAndStatusVisit(Doctor doctor, LocalDate startWeek, LocalDate finishWeek, StatusVisit statusVisit);


//    @Query("SELECT v FROM Visits v WHERE v.dateOfVisit = :dateOfVisit" +
//            " AND (:start < v.startVisit AND v.startVisit < :finish)" +
//            " OR (:start < v.finishVisit AND v.finishVisit < :finish)")
//
//    List<Visits> findVisitByDateTimeRange(@Param("dateOfVisit") LocalDate dateOfVisit,
//                                          @Param("start") LocalTime startOfVisit,
//                                          @Param("finish") LocalTime finishOfVisit);

    @Query("""
        SELECT v FROM Visits v 
        WHERE v.dateOfVisit = :date 
          AND (
            (:start < v.finishVisit AND :finish > v.startVisit)
          )
    """)
    List<Visits> findOverlappingVisits(@Param("date") LocalDate date,
                                       @Param("start") LocalTime start,
                                       @Param("finish") LocalTime finish);

}
