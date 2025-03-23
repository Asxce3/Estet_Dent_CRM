package org.example.test_orm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MedHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @Column(nullable = false)
    private boolean status;

    public MedHistory(boolean status, Patient patient) {
        this.status = status;
        this.patient = patient;
    }
}
