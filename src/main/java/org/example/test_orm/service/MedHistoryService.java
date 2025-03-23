package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.MedHistory;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.repository.MedHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedHistoryService {
    private final MedHistoryRepository medHistoryRepository;

    public void createMedHistoryPatient(Patient patient) {
        medHistoryRepository.save(new MedHistory(true, patient));
    }

    public List<MedHistory> getMedHistory(long patientId) {
        return medHistoryRepository.getMedHistoriesByPatient_ID(patientId);
    }
}
