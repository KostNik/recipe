package com.edu.recipies.command;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryCommand {

    private Long id;
    private String description;
    private Set<RecipeCommand> recipes = new HashSet<>();

}
