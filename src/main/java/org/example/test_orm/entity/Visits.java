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
    private long ID;

    @ManyToOne
    @JoinColumn(name = "med_history_id", referencedColumnName = "id")
    private MedHistory medHistory;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private String complaint;

    @Enumerated(EnumType.STRING)
    private StatusVisit statusVisit;

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
