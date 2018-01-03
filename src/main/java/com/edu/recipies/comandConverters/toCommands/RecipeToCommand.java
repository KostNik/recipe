package com.edu.recipies.comandConverters.toCommands;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToCommand implements Converter<Recipe, RecipeCommand> {

    @Nullable
    @Override
    @Synchronized
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }
        return null;
    }
}
