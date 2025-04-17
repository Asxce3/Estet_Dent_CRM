package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Entity
@Getter
@Setter
//@ToString
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Directory parent;

    private long threeId;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Transient
    private List<DirectoryValue> values;




}
