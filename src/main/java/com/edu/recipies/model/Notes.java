package com.edu.recipies.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Notes {

    private String id;

    private String recipeNotes;

    private Recipe recipe;

}
