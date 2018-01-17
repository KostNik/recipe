package com.edu.recipies.repository;

import com.edu.recipies.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Optional<Ingredient> findByRecipeIdAndId(String recipeId, String ingredientId);

}
