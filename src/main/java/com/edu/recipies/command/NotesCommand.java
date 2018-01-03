package com.edu.recipies.command;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotesCommand {

    private Long id;
    private String recipeNotes;
    private RecipeCommand recipeCommand;

}
