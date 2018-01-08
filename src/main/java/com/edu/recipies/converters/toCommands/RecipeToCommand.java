package com.edu.recipies.converters.toCommands;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static com.edu.recipies.RecipeUtils.isValidCollection;

@Component
public class RecipeToCommand implements Converter<Recipe, RecipeCommand> {


    private final CategoryToCommand   categoryConverter;
    private final IngredientToCommand ingredientConverter;
    private final NotesToCommand      notesConverter;

    public RecipeToCommand(CategoryToCommand categoryConverter,
                           IngredientToCommand ingredientConverter,
                           NotesToCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Nullable
    @Override
    @Synchronized
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setImage(source.getImage());
        command.setNotes(notesConverter.convert(source.getNotes()));

        if (isValidCollection(source.getCategories())) {
            source.getCategories().stream()
                    .map(categoryConverter::convert)
                    .forEach(command.getCategories()::add);
        }

        if (isValidCollection(source.getIngredients())) {
            source.getIngredients().stream()
                    .map(ingredientConverter::convert)
                    .forEach(command.getIngredients()::add);
        }

        return command;
    }
}
