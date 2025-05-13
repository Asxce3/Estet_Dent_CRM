package org.example.test_orm.service;

import lombok.RequiredArgsConstructor;
import org.example.test_orm.DTO.MedCardDTO;
import org.example.test_orm.DTO.MedCardMaterialsDTO;
import org.example.test_orm.entity.Document;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.repository.MedCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedCardService {
    private final VisitsService visitsService; // TODO в будщем заменить костыл (САМВЕЛ)
    private final MedCardRepository medCardRepository;
    private final MaterialService materialService;

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

    public void create(MedCardDTO medCardDTO) {
        MedCard medCard = new MedCard();
        medCard.setVisits(visitsService.getVisit(medCardDTO.getVisitId()));
        medCard.setAnamnesis(medCardDTO.getAnamnesis());
        medCard.setAllergies(medCardDTO.getAllergies());
        medCard.setBotkin(medCardDTO.getBotkin());
        medCard.setDiagnosis(medCardDTO.getDiagnosis());
        medCard.setObjective(medCardDTO.getObjective());
        medCard.setRecommendations(medCardDTO.getRecommendations());
        medCard.setTreatment(medCardDTO.getTreatment());
        medCard.setComplaints(medCardDTO.getObjective());

        for(MedCardMaterialsDTO material: medCardDTO.getMaterials()) {
            medCard.addMedCardMaterial(materialService.getMaterialById(material.getId()), material.getCount());
        }

        for(Document document: medCardDTO.getDocuments()) {
            medCard.addDocument(document);
        }
        medCardRepository.save(medCard);

    }


    public List<MedCard> getMedCardsByPatientID(long patientId) {
        return medCardRepository.findMedCardsByVisitsPatientID(patientId);
    }


}
