package org.example.test_orm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Entity
@Getter
@Setter
@Validated
public class Teeth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer quadrant;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer number;

    @ManyToOne
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JsonIgnore
    private ToothCondition toothCondition;

    @Override
    public String toString() {
        return String.format("id = %s, quadrant = %s, number = %s, patient = %s, toothCondition = %s",
                id, quadrant, number, patient.getID(), toothCondition.getId());
    }

}
