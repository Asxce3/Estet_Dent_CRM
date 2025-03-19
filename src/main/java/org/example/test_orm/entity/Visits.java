package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Visits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitsID;

    @OneToMany
    private List<Document> documentList;

    @OneToMany
    private List<CompletedWork> completedWork;

    @Column(nullable = false)
    private LocalTime startVisit;

    @Column(nullable = false)
    private LocalTime finishVisit;

    @Column(nullable = false)
    private LocalDate dateOfVisit;

}
