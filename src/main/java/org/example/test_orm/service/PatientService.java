package org.example.test_orm.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.teeth.TeethDTO;
import org.example.test_orm.DTO.teeth.ToothConditionDTO;
import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.entity.Teeth;
import org.example.test_orm.exception.CreateDataOfBirthPatientException;
import org.example.test_orm.exception.PatientNotFoundException;
import org.example.test_orm.repository.PatientRepository;
import org.example.test_orm.service.auth.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PatientService {
    private final AuthService authService;
    private final MedHistoryService medHistoryService;
    private final PatientRepository patientRepository;
    private final TeethService teethService;

    public List<Patient> getPatients(Cookie[] cookies) {
        Doctor doctor = authService.getDoctorFromCookie(cookies);
        return patientRepository.findPatientsByDoctor(doctor);
    }

    public List<Patient> getPatients() {        // Тестово
        return patientRepository.findAll();
    }

    public Patient getPatient(long id) {
        Optional<Patient> optPatient = patientRepository.findById(id);
        if(optPatient.isPresent()) {
            return optPatient.get();
        }   else {
            throw new PatientNotFoundException("Patient not found!");
        }
    }

    public List<Patient> getPatientForInputName(String name) {
        return patientRepository.findByNameStartingWith(name);
    }

    public List<Patient> getPatientForInputName(String name, Doctor doctor) {
        return patientRepository.findByNameStartingWithAndDoctor(name, doctor);
    }
    @Transactional
    public void createPatient(Patient patient, Cookie[] cookies) {
        try {
            if(LocalDate.now().isAfter(patient.getBirthDate())) {
                patient.setDoctor(authService.getDoctorFromCookie(cookies));
                Patient savedPatient = patientRepository.saveAndFlush(teethService.createPatientTeeth(patient)); // TODO (Самвел) в будущем заменить на тригеры (это костыль)
                medHistoryService.createMedHistoryPatient(savedPatient);
            }   else {
                throw new CreateDataOfBirthPatientException("The patient's date of birth cannot be later than the current date.");
            }
        }   catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public void deletePatient(long id) {
        patientRepository.deleteById(id);
        if (patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not deleted!");
        }
    }


//    -------Teeth logic-------
    public List<TeethDTO> getPatientTeeth(long patientId) {
        return teethService.getTeeth(patientId);
    }

    public List<ToothConditionDTO> getTeethCondition() {
        return teethService.getToothCondition();
    }

    public void updatePatientTeeth(long patientId, List<TeethDTO> patientTeeth) {
        Optional<Patient> optPatient = patientRepository.findById(patientId);
        if(optPatient.isEmpty()) {
            throw new PatientNotFoundException("Patient not found!");
        }
        Patient patient = optPatient.get();
        patientRepository.save(teethService.updatePatientTeeth(patient, patientTeeth));
    }


}
