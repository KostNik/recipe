package com.edu.recipies.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

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
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String     url;
    private String     source;
    private Integer    servings;
    private Integer    cookTime;
    private Integer    prepTime;
    private String     description;
    @Lob
    private String     directions;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @Lob
    private Byte[]     image;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
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
