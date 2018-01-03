package com.edu.recipies.comandConverters.fromCommands;

import com.edu.recipies.commands.NotesCommand;
import com.edu.recipies.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommandToNotes implements Converter<NotesCommand, Notes> {

    @Nullable
    @Override
    @Synchronized
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
