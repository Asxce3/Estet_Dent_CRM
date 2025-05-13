package org.example.test_orm.DTO;

import lombok.Data;
import org.example.test_orm.entity.Document;

import java.util.*;

@Data
public class MedCardDTO {
    private String diagnosis;
    private String complaints;
    private String anamnesis;
    private String treatment;
    private String objective;
    private String recommendations;
    private String allergies;
    private String botkin;
    private Long visitId;

    private List<MedCardMaterialsDTO> materials = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();
}
