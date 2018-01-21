package com.edu.recipies.service;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.converters.fromCommands.CommandToRecipe;
import com.edu.recipies.converters.toCommands.RecipeToCommand;
import com.edu.recipies.exceptions.NotFoundException;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import com.edu.recipies.repository.reactive.RecipeReactiveRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeReactiveRepository recipeRepository;

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
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));

        Recipe recipeReturned = recipeService.findById("1L").block();

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId("1L");
        Mono<Recipe> recipeOptional = Mono.just(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");

        when(recipeToCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById("1L").blockOptional().orElse(null);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));

        List<Recipe> savedRecipes = recipeService.getRecipes().collectList().block();
        assertEquals(savedRecipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void saveRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("ONE");

        when(recipeRepository.save(any())).thenReturn(Mono.empty());

        recipeService.saveRecipeCommand(recipeCommand);

        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void findCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId("ONE");
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("ONE");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeToCommand.convert(any())).thenReturn(recipeCommand);
        Mono<RecipeCommand> commandById = recipeService.findCommandById("ONE");
        assertEquals("ONE", commandById.block().getId());
    }

    @Test
    public void testDeleteById() {
        String id = "3L";
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
        recipeService.deleteById(id);
        verify(recipeRepository, times(1)).deleteById(anyString());
    }
}