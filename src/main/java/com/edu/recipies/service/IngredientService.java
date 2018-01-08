package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;

import java.util.Optional;

public interface IngredientService {

    Optional<IngredientCommand> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    Optional<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand);

    Boolean deleteIngredient(Long id);
}
