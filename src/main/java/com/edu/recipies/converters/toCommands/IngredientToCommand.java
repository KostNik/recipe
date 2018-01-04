package com.edu.recipies.converters.toCommands;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.model.Ingredient;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToCommand implements Converter<Ingredient, IngredientCommand> {


    private final UnitOfMeasureToCommand unitOfMeasureToCommand;

    @Autowired
    public IngredientToCommand(UnitOfMeasureToCommand unitOfMeasureToCommand) {this.unitOfMeasureToCommand = unitOfMeasureToCommand;}


    @Nullable
    @Override
    @Synchronized
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setAmount(source.getAmount());
        command.setDescription(source.getDescription());
        command.setId(source.getId());
        command.setUnitOfMeasureCommand(unitOfMeasureToCommand.convert(source.getUnitOfMeasure()));

        return command;
    }
}
