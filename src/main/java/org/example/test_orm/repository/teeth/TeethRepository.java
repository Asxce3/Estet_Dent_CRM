package org.example.test_orm.repository.teeth;

import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.Teeth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeethRepository extends JpaRepository<Teeth, Long> {
    List<Teeth> getTeethByPatient(Patient patient);
    List<Teeth> getTeethByPatientID(long id);

}
