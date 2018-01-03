package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        return null;
    }
}
