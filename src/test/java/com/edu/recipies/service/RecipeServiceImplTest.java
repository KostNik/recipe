package com.edu.recipies.service;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.converters.fromCommands.CommandToRecipe;
import com.edu.recipies.converters.toCommands.RecipeToCommand;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToCommand recipeToCommand;

    @Mock
    private CommandToRecipe commandToRecipe;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, commandToRecipe, recipeToCommand);
    }

    @Test
    public void getRecipeById() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));

        Optional<Recipe> recipeReturned = recipeService.findById(1L);

        assertTrue(recipeReturned.isPresent());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L).orElse(null);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {

        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipes);

        Set<Recipe> savedRecipes = recipeService.getRecipes();
        assertEquals(savedRecipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
    }

    @Test
    public void saveRecipeCommand() {
    }

    @Test
    public void findCommandById() {
    }

    @Test
    public void testDeleteById() {
        Long id = 3L;
        recipeService.deleteById(id);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}