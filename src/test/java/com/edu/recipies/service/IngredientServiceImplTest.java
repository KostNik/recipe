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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class IngredientServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;


    private IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientRepository,
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
        command.setId(3L);
        command.setRecipeId(2L);

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        savedRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        Optional<IngredientCommand> savedCommand = ingredientService.saveOrUpdateIngredient(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.get().getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteIngredientByID() {
        ingredientService.deleteIngredient(anyLong());
        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }

}