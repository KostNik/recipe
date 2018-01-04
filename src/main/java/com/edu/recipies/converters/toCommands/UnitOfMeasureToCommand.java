package com.edu.recipies.converters.toCommands;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {


    @Nullable
    @Override
    @Synchronized
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }
        final UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }
}
