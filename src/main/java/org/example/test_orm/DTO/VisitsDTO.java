package org.example.test_orm.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.test_orm.DTO.transfer.Update;
import org.example.test_orm.entity.CompletedWork;
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
    @Null(groups = Update.class)
    private Long medHistoryId;
    @Null(groups = Update.class)

    private Long patientId;
    @Null(groups = Update.class)

    private String complaint;

    private StatusVisit statusVisit = StatusVisit.CREATED;
    private List<Document> documentList;

    private List<CompletedWork> completedWork;

    @NotNull(groups = Update.class)
    private LocalTime startVisit;

    @NotNull(groups = Update.class)
    private LocalTime finishVisit;

    @NotNull(groups = Update.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfVisit;
}
