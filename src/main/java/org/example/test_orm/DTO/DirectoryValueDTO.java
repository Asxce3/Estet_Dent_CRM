package org.example.test_orm.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DirectoryValueDTO {
    private long id;
    private long directoryId;
    @NotEmpty(message = "Поле ввода не должно быть пустым")
    private String name;

}
