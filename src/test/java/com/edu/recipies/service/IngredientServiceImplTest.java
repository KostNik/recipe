package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.converters.fromCommands.CommandToIngredient;
import com.edu.recipies.converters.fromCommands.CommandToUnitOfMeasure;
import com.edu.recipies.converters.toCommands.IngredientToCommand;
import com.edu.recipies.converters.toCommands.UnitOfMeasureToCommand;
import com.edu.recipies.model.Ingredient;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.IngredientRepository;
import com.edu.recipies.repository.RecipeRepository;
import com.edu.recipies.repository.UnitOfMeasureRepository;
import com.edu.recipies.repository.reactive.RecipeReactiveRepository;
import com.edu.recipies.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class IngredientServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;


    private IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(
                new IngredientToCommand(new UnitOfMeasureToCommand()),
                new CommandToIngredient(new CommandToUnitOfMeasure()),
                new CommandToUnitOfMeasure(),
                recipeRepository,
                unitOfMeasureRepository);
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3L");
        command.setRecipeId("2L");

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3L");
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        Mono<IngredientCommand> savedCommand = ingredientService.saveOrUpdateIngredient(command);

        //then
        assertEquals("3L", savedCommand.block().getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteIngredientByID() {
        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3L");
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(savedRecipe));
        ingredientService.deleteIngredient("", "");

        verify(recipeRepository, times(1)).findById(anyString());
    }

}