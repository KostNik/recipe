package com.edu.recipies.config;

import com.edu.recipies.converters.StringToCategoryConverter;
import com.edu.recipies.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

@Component
public class WebFluxConfig {


    @Autowired
    public void addCategoryServiceFormatter(FormatterRegistry registry, CategoryService categoryService) {
        registry.addConverter(new StringToCategoryConverter(categoryService));
    }
}
