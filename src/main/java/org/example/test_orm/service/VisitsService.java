package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_orm.DTO.VisitsDTO;
import org.example.test_orm.entity.*;
import org.example.test_orm.repository.MedHistoryRepository;
import org.example.test_orm.repository.VisitsRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitsService {
    private final VisitsRepository visitsRepository;
    private final MedHistoryRepository medHistoryRepository;
    private final CheckDate checkDate = new CheckDate();

    public List<Visits> getPatientVisits(long patientId) {
        return visitsRepository.findVisitsByPatientID(patientId);
    }

    public List<VisitsDTO> getPatientVisitsDTO(long patientId) {
        return visitsRepository.findVisitsByPatientID(patientId).stream().map(this::getVisitDTO).toList();
    }



    public List<Visits> getVisits(LocalDate startWeek, Doctor doctor) {
        return visitsRepository.
                findByPatientDoctorAndDateOfVisitBetweenAndStatusVisit
                        (doctor, startWeek,  startWeek.plusDays(6), StatusVisit.CREATED);
    }

    public void deleteVisits(long id) {
        visitsRepository.deleteById(id);
    }

    public void canselVisit(long id) {
        Visits visits = getVisit(id);
        visits.setStatusVisit(StatusVisit.CANCELLED);
        visitsRepository.save(visits);
    }

    public Visits getVisit(long id) {
        Optional<Visits> optVisits = visitsRepository.findById(id);
        if (optVisits.isEmpty()) {
            throw new RuntimeException("Визит не сушествует");
        }
        return optVisits.get();
    }


    public VisitsDTO getVisitDTO(Visits visits) {
        VisitsDTO visitsDTO = new VisitsDTO();
        visitsDTO.setId(visits.getID());
        visitsDTO.setDateOfVisit(visits.getDateOfVisit());
        visitsDTO.setStartVisit(visits.getStartVisit());
        visitsDTO.setFinishVisit(visits.getFinishVisit());
        visitsDTO.setComplaint(visits.getComplaint());
        visitsDTO.setMedHistoryId(visits.getMedHistory().getID());
        visitsDTO.setPatientId(visits.getPatient().getID());

        return visitsDTO;
    }


    public void updateVisits(VisitsDTO visitsDTO) {
        try{
            if(checkTime(visitsDTO.getStartVisit(), visitsDTO.getFinishVisit())) {
                Visits visits = getVisit(visitsDTO.getId());
                visits.setDateOfVisit(visitsDTO.getDateOfVisit());
                visits.setStartVisit(visitsDTO.getStartVisit());
                visits.setFinishVisit(visitsDTO.getFinishVisit());
                visitsRepository.save(visits);
                log.info("Visits update successful!");
            }
        }   catch (DataIntegrityViolationException e) {
            log.warn("Visits update failed", e);
            throw new DataIntegrityViolationException("Visits update failed", e);
        }
    }

    public void createVisits(VisitsDTO visitsDTO) {
        try {
            if(checkTime(visitsDTO.getStartVisit(), visitsDTO.getFinishVisit())) {

                int visitsSize = visitsRepository.findVisitByDateTimeRange(
                        visitsDTO.getDateOfVisit(),
                        visitsDTO.getStartVisit(),
                        visitsDTO.getFinishVisit()).size();
                if (visitsSize > 0) {
                    throw new RuntimeException("Выбранное время пересекается с другими визитами");
                }

                MedHistory medHistory = getMedHistory(visitsDTO.getMedHistoryId());
                visitsRepository.save(
                        Visits.builder()
                                .dateOfVisit(visitsDTO.getDateOfVisit())
                                .startVisit(visitsDTO.getStartVisit())
                                .finishVisit(visitsDTO.getFinishVisit())
                                .patient(medHistory.getPatient())
                                .medHistory(medHistory)
                                .complaint(visitsDTO.getComplaint())
                                .statusVisit(visitsDTO.getStatusVisit())
                                .build()
                );
                log.info("Создание визита");
            }
            log.info("Не удалось сохранить визит");
        }   catch (DataIntegrityViolationException e) {
            log.warn("Не удалось создать запись Visits", e);
            throw new DataIntegrityViolationException("Не удалось создать запись Visits", e);
        }
    }

    public MedHistory getMedHistory(long id) {
        return medHistoryRepository.findById(id).orElseThrow(() -> new RuntimeException("MedHistoryNotFound"));
    }

    private boolean checkTime(LocalTime start, LocalTime finish) {
        return checkDate.checkWorkTime(start, finish) && checkDate.checkFinishBeforeStart(start, finish);
    }

    public LocalDate parseOrCreateDate(String stringDate) {
        if(stringDate == null) {
            int i = LocalDate.now().getDayOfWeek().getValue();
            return LocalDate.now().minusDays(i - 1);    // Возрват текущего пн
        }
        return LocalDate.parse(stringDate);
    }
}
