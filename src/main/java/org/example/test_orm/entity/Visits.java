package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @OneToMany(mappedBy = "visits", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedCard> medCards =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "med_history_id", referencedColumnName = "id")
    private MedHistory medHistory;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private String complaint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVisit statusVisit;

    @Column(nullable = false)
    private LocalTime startVisit;

    @Column(nullable = false)
    private LocalTime finishVisit;

    @Column(nullable = false)
    private LocalDate dateOfVisit;


}
