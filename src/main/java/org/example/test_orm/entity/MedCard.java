package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class MedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne
    private Visits visits;

    private String diagnosis;
    private String complaints;
    private String anamnesis;
    private String treatment;
    private String objective;
    private String recommendations;
    private String allergies;
    private String botkin;

}
