package com.edu.recipies.converters.fromCommands;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class CommandToIngredient implements Converter<IngredientCommand, Ingredient> {


    private final CommandToUnitOfMeasure uomConverter;

    public CommandToIngredient(CommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    @Synchronized
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasureCommand()));
        return ingredient;
    }
}
