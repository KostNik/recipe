package com.edu.recipies.service;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Optional<Recipe> findById(Long id);

    Optional<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

    Optional<RecipeCommand> findCommandById(Long id);


}
