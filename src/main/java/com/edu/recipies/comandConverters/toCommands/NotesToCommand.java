package com.edu.recipies.comandConverters.toCommands;

import com.edu.recipies.commands.NotesCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Notes;
import com.edu.recipies.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToCommand implements Converter<Notes, NotesCommand> {

    @Nullable
    @Override
    @Synchronized
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
