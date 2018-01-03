package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        return null;
    }
}
