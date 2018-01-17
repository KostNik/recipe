package com.edu.recipies.service;

import com.edu.recipies.converters.toCommands.RecipeToCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeServiceIT {

    private final static String NEW_URL = "fake_url";

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeToCommand recipeToCommand;

    @Test
    @Transactional
    public void testSaveOfRecipeUrl() {
        Recipe recipe = recipeRepository.findAll().iterator().next();
        recipe.setUrl(NEW_URL);
        RecipeCommand converted = recipeToCommand.convert(recipe);
        Optional<RecipeCommand> savedRecipeCommand = recipeService.saveRecipeCommand(converted);

        assertEquals(NEW_URL, savedRecipeCommand.get().getUrl());
        assertEquals(recipe.getId(), savedRecipeCommand.get().getId());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.get().getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.get().getIngredients().size());
    }

}