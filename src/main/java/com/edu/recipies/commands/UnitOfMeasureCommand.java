package com.edu.recipies.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UnitOfMeasureCommand {

    private String id;
    private String description;

}
