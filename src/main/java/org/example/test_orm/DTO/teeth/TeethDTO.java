package org.example.test_orm.DTO.teeth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeethDTO {
    private Long id;
    private Long patientId;
    private Long toothConditionId;
    private Integer number;
    private Integer quadrant;
}
