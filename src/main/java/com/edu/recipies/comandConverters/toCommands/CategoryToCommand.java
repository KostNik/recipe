package com.edu.recipies.comandConverters.toCommands;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCommand implements Converter<Category, CategoryCommand> {


    @Nullable
    @Override
    @Synchronized
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        final CategoryCommand command = new CategoryCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }


}
