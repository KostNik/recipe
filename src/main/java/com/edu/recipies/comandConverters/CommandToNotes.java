package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.NotesCommand;
import com.edu.recipies.model.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    public Notes convert(NotesCommand source) {
        return null;
    }
}
