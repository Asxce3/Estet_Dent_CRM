package org.example.test_orm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "directory")
@Getter
@Setter
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Поле ввода не должно быть пустым")
    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_id")
    private Directory parent;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Override
    public String toString() {
        String res = "{id : %s, parent : %s, name : %s, category: %s}";
        return String.format(res, id, parent, name, category);
    }


}
