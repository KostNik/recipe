package com.edu.recipies.repository;

import com.edu.recipies.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Optional<Ingredient> findByRecipeIdAndId(Long recipeId, Long ingredientId);

}
