package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandToRecipe implements Converter<RecipeCommand, Recipe> {
    @Override
    public Recipe convert(RecipeCommand source) {
        return null;
    }
}
