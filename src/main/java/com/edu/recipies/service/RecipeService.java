package com.edu.recipies.service;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    Optional<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

    Optional<RecipeCommand> findCommandById(Long id);

    void deleteById(Long id);

}
