package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand);

    Boolean deleteIngredient(String recipeId, String ingredientId);
}
