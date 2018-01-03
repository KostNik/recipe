package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCommand implements Converter<Category, CategoryCommand> {


    @Override
    public CategoryCommand convert(Category source) {
        CategoryCommand command = new CategoryCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }


}
