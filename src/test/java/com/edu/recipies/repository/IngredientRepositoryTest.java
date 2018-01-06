package com.edu.recipies.repository;

import com.edu.recipies.model.Ingredient;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.IngredientRepository;
import com.edu.recipies.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@DataJpaTest
@RunWith(SpringRunner.class)
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testFindByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();

        Ingredient ingredient_1 = new Ingredient("one");
        Ingredient ingredient_2 = new Ingredient("two");
        Ingredient ingredient_3 = new Ingredient("three");

        recipe.addIngredient(ingredient_1);
        recipe.addIngredient(ingredient_2);
        recipe.addIngredient(ingredient_3);

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> ingredient = ingredientRepository.findByRecipeIdAndId(1L, 2L);
        assertTrue(ingredient.isPresent());
        assertEquals(Long.valueOf(2L), ingredient.get().getId());
        assertEquals(Long.valueOf(1L), savedRecipe.getId());
    }
}