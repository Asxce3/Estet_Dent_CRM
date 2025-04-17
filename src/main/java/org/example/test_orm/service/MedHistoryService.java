package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.MedHistoryDTO;
import org.example.test_orm.entity.MedHistory;
import org.example.test_orm.entity.Patient;
import org.example.test_orm.repository.MedCardRepository;
import org.example.test_orm.repository.MedHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedHistoryService {
    private final MedHistoryRepository medHistoryRepository;
    private final MedCardRepository medCardRepository;


    public void createMedHistoryPatient(Patient patient) { // TODO способ создания названия мед истории можно пересмотреть
        medHistoryRepository.save(new MedHistory(true, patient, "Первая мед история пациента"));
    }

    public List<MedHistory> getMedHistories(long patientId) {
        return medHistoryRepository.getMedHistoriesByPatient_ID(patientId);
    }

    public void create(MedHistoryDTO medHistoryDTO) {

        List<MedHistory> listMedHistory = medHistoryRepository.getMedHistoriesByPatient_ID(medHistoryDTO.getPatientId());
        if(listMedHistory.isEmpty()) {
            throw new RuntimeException("Такого пациента не существует");
        }
        Patient patient = listMedHistory.get(0).getPatient();
        medHistoryRepository.save(new MedHistory(true, patient, medHistoryDTO.getName()));
    }
}
