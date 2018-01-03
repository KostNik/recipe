package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToCommand implements Converter<Ingredient, IngredientCommand> {
    @Override
    public IngredientCommand convert(Ingredient source) {
        return null;
    }
}
