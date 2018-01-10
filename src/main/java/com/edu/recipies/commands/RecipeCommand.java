package com.edu.recipies.commands;

import com.edu.recipies.model.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RecipeCommand {

    private Long       id;
    @URL
    private String     url;
    private String     source;
    @Min(1)
    @Max(60)
    private Integer    servings;
    @Min(1)
    @Max(360)
    private Integer    cookTime;
    @Min(1)
    @Max(360)
    private Integer    prepTime;
    @NotBlank
    @Size(min = 2, max = 255)
    private String     description;
    @NotBlank
    private String     directions;
    private Difficulty difficulty;
    private Byte[]     image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand>   categories  = new HashSet<>();
    private NotesCommand notes;

}
