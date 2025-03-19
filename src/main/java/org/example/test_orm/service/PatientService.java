package org.example.test_orm.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.Doctor;
import org.example.test_orm.entity.Patient;
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

    public List<Patient> getPatients(Cookie[] cookies) {
        Doctor doctor = getDoctorFromToken(cookies);
        return patientRepository.findPatientsByDoctor(doctor);
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
    @Transactional
    public void createPatient(Patient patient, Cookie[] cookies) {
        try {
            if(LocalDate.now().isAfter(patient.getBirthDate())) {
                patient.setDoctor(getDoctorFromToken(cookies));
                Patient savedPatient = patientRepository.saveAndFlush(patient); // TODO (Самвел) в будущем заменить на тригеры (это костыль)
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

    private Doctor getDoctorFromToken(Cookie[] listCookies) {
        String token = "";
        for(Cookie cookie: listCookies) {
            if (cookie.getName().equals("access_token") || cookie.getName().equals("refresh_token") ) {
                token = cookie.getValue();
                String username = authService.parseToken(token);
                return authService.getDoctorByLogin(username);
            }
        }
        throw new RuntimeException("Не удалось получить доктора из токенов");
    }
}
