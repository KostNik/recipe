package com.edu.recipies.converters.fromCommands;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static com.edu.recipies.RecipeUtils.isValidCollection;

@Component
public class CommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CommandToNotes      commandToNotes;
    private final CommandToCategory   commandToCategory;
    private final CommandToIngredient commandToIngredient;

    @Autowired
    public CommandToRecipe(CommandToNotes commandToNotes,
                           CommandToCategory commandToCategory,
                           CommandToIngredient commandToIngredient) {
        this.commandToNotes = commandToNotes;
        this.commandToCategory = commandToCategory;
        this.commandToIngredient = commandToIngredient;
    }

    @Nullable
    @Override
    @Synchronized
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setImage(source.getImage());
        recipe.setNotes(commandToNotes.convert(source.getNotes()));

        if (isValidCollection(source.getCategories())) {
            source.getCategories().stream()
                    .map(commandToCategory::convert)
                    .forEach(recipe.getCategories()::add);
        }

        if (isValidCollection(source.getIngredients())) {
            source.getIngredients().stream()
                    .map(commandToIngredient::convert)
                    .forEach(recipe.getIngredients()::add);
        }

        return recipe;
    }


}
