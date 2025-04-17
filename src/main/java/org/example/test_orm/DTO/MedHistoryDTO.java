package org.example.test_orm.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MedHistoryDTO {

    private long ID;

    private long patientId;

    private boolean status;

    private String name;

}
