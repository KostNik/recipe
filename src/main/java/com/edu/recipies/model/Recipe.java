package com.edu.recipies.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class Recipe {

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
    private Set<Ingredient> ingredients = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
    private Notes notes;


    public void setNotes(Notes notes) {
        this.notes = notes;
        if (Objects.nonNull(notes))
            notes.setRecipe(this);
    }

    public void addIngredient(Ingredient ingredient) {
        if (Objects.nonNull(ingredient)) {
            ingredients.add(ingredient);
            ingredient.setRecipe(this);
        }
    }

}
