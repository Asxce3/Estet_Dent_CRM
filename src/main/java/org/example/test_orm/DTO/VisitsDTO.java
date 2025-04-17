package org.example.test_orm.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.test_orm.entity.MedCard;
import org.example.test_orm.entity.Document;
import org.example.test_orm.entity.StatusVisit;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString

public class VisitsDTO {

    private long id;
    private Long medHistoryId;
    private Long patientId;

    private String complaint;

    private StatusVisit statusVisit = StatusVisit.CREATED;
    private List<Document> documentList;

    private List<MedCard> completedWork;

    private LocalTime startVisit;

    private LocalTime finishVisit;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfVisit;
}
