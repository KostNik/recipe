package com.edu.recipies.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @DBRef
    private Set<Category>    categories  = new HashSet<>();
    private Notes notes;


    public void addIngredient(Ingredient ingredient) {
        if (Objects.nonNull(ingredient)) {
            ingredients.add(ingredient);
//            ingredient.setRecipe(this);
        }
    }

}
