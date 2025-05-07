package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.entity.Visits;
import org.example.test_orm.repository.MedCardRepository;
import org.example.test_orm.repository.TeethRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedCardService {
    private final VisitsService visitsService; // TODO в будщем заменить костыл (САМВЕЛ)
    private final MedCardRepository medCardRepository;
    private final TeethRepository teethRepository;

    public MedCard getNewMedCard(long id) {
        MedCard medCard = new MedCard();
        medCard.setVisits(visitsService.getVisit(id));
        return medCard;
    }

    public MedCard getMedCardByID(long id) {
        Optional<MedCard> optionalMedCard = medCardRepository.findById(id);
        if(optionalMedCard.isEmpty()) {
            throw new RuntimeException("Такой мед карточки не существует");
        }
        return optionalMedCard.get();
    }

    public void create(MedCard medCard) {
        medCardRepository.save(medCard);
    }

    public List<MedCard> getMedCardsByPatientID(long patientId) {
        return medCardRepository.findMedCardsByVisitsPatientID(patientId);
    }


}
