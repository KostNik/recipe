package com.edu.recipies.comandConverters.fromCommands;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommandToCategory implements Converter<CategoryCommand, Category> {

    @Nullable
    @Override
    @Synchronized
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }
        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }


}
