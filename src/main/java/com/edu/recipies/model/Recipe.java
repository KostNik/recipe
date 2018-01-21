package com.edu.recipies.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;

    private String     url;
    private String     source;
    private Integer    servings;
    private Integer    cookTime;
    private Integer    prepTime;
    private String     description;
    private String     directions;
    private Difficulty difficulty;
    private Byte[]     image;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Set<Category>    categories  = new HashSet<>();
    private Notes notes;


    public void addIngredient(Ingredient ingredient) {
        if (Objects.nonNull(ingredient)) {
            ingredients.add(ingredient);
//            ingredient.setRecipe(this);
        }
    }

}
