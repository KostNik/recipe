package com.edu.recipies.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = {"description", "amount", "unitOfMeasureCommand"})
public class IngredientCommand {

    private String               id;
    private String               recipeId;
    @NotBlank
    private String               description;
    @Min(1)
    @NotNull
    private BigDecimal           amount;
    @NotNull
    private UnitOfMeasureCommand unitOfMeasureCommand;

}
