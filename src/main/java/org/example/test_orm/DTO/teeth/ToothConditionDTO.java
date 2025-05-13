package org.example.test_orm.DTO.teeth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ToothConditionDTO {

    private Long id;
    private String condition;
    private String symbol;

}
