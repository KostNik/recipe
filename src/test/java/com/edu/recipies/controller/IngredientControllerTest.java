package com.edu.recipies.controller;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.service.IngredientService;
import com.edu.recipies.service.RecipeService;
import com.edu.recipies.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private IngredientController ingredientController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }


    @Test
    public void testShowAllIngredients() throws Exception {
        RecipeCommand value = new RecipeCommand();
        value.setId("1");
        when(recipeService.findCommandById(anyString())).thenReturn(Optional.of(value));


        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testShowAllIngredientsWhenNoRecipe() throws Exception {
        when(recipeService.findCommandById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/all"));

    }

    @Test
    public void testShowIngredients() throws Exception {
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Optional.of(new IngredientCommand()));

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Optional.of(new IngredientCommand()));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Sets.newSet());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("3");

        when(recipeService.findCommandById(anyString())).thenReturn(Optional.of(recipeCommand));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Sets.newSet());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void testSaveIngredientCommand() throws Exception {
        IngredientCommand value = new IngredientCommand();
        value.setId("2");
        when(ingredientService.saveOrUpdateIngredient(any(IngredientCommand.class))).thenReturn(Optional.of(value));

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));

    }

    @Test
    public void testDeleteIngredient() throws Exception {
        when(ingredientService.deleteIngredient(anyString())).thenReturn(true);

        mockMvc.perform(delete("/recipe/1/ingredient/1"))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).deleteIngredient(anyString());
    }
}