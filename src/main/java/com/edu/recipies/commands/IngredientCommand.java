package com.edu.recipies.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = {"description", "amount", "unitOfMeasureCommand"})
public class IngredientCommand {

    private Long                 id;
    private Long                 recipeId;
    private String               description;
    private BigDecimal           amount;
    private UnitOfMeasureCommand unitOfMeasureCommand;


}
