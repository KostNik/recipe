package com.edu.recipies.comandConverters;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandToCategory implements Converter<CategoryCommand, Category> {

    @Override
    public Category convert(CategoryCommand source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }


}
