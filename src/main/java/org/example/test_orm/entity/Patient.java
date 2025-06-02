package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.test_orm.annotation.phone.ValidPhone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ValidPhone
    @Column(nullable = false, unique = true)
    private String telephoneNumber;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "username_id", referencedColumnName = "id", nullable = false)
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teeth> teethList = new ArrayList<>();

    public void addTeeth(Teeth teeth) {
        this.teethList.add(teeth);
        teeth.setPatient(this);
    }

    public void removeTeeth(Teeth teeth) {
        this.teethList.remove(teeth);
        teeth.setPatient(null);
    }
}
