package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.NotesCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Notes;
import com.edu.recipies.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToCommand implements Converter<Notes, NotesCommand> {
    @Override
    public NotesCommand convert(Notes source) {
        return null;
    }
}
