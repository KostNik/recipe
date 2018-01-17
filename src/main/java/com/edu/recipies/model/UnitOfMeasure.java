package com.edu.recipies.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UnitOfMeasure {

    private String id;

    private String description;

}
