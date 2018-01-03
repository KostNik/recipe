package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import org.springframework.core.convert.converter.Converter;

public class RecipeToCommand implements Converter<Recipe, RecipeCommand> {
    @Override
    public RecipeCommand convert(Recipe source) {
        return null;
    }
}
