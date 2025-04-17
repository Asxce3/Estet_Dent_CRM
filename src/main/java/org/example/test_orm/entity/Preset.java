package org.example.test_orm.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString
public class Preset {

    private List<Diagnose> diagnoseList;

}
