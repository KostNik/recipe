package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    @Override
    public Ingredient convert(IngredientCommand source) {
        return null;
    }
}
