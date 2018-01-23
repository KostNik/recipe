package com.edu.recipies.converters;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToCategoryConverter implements Converter<String, CategoryCommand> {

    private final CategoryService categoryService;

    public StringToCategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public CategoryCommand convert(String source) {
        return categoryService.getById(source).block();
    }
}
