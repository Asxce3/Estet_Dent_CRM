package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.MedHistory;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.repository.MedHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedHistoryService {
    private final MedHistoryRepository medHistoryRepository;

    public void createMedHistoryPatient(Patient patient) {
        medHistoryRepository.save(new MedHistory(true, patient));
    }
}
