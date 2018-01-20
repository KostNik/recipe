package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);

    Mono<RecipeCommand> findCommandById(String id);

    void deleteById(String id);

}
