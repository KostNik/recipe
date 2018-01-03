package com.edu.recipies.comandConverters.fromCommands;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Nullable
    @Override
    @Synchronized
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(source.getDescription());
        unitOfMeasure.setId(source.getId());
        return unitOfMeasure;
    }

}
