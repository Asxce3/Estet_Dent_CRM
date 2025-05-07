package org.example.test_orm.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.test_orm.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class DirectoryDTO {
    private Long id;
    private Long threeId;
    private Long parentId;
    @NotEmpty(message = "Поле ввода не должно быть пустым")
    private String name;
    private Category category;
    private List<DirectoryValueDTO> values = new ArrayList<>();
    private List<DirectoryDTO> children = new ArrayList<>();

}
