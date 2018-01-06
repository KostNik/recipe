package com.edu.recipies.controller;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Ingredient;
import com.edu.recipies.model.Recipe;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private IngredientController ingredientController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
    }


    @Test
    public void testShowAllIngredients() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        RecipeCommand value = new RecipeCommand();
        value.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(Optional.of(value));


        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testShowAllIngredientsWhenNoRecipe() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/all"));

    }

    @Test
    public void testShowIngredients() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(Optional.of(new IngredientCommand()));

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(Optional.of(new IngredientCommand()));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Sets.newSet());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);

        when(recipeService.findCommandById(anyLong())).thenReturn(Optional.of(recipeCommand));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Sets.newSet());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uoms"))
                .andExpect(view().name("recipe/ingredient/ingredientform"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testSaveIngredientCommand() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        IngredientCommand value = new IngredientCommand();
        value.setId(2L);
        when(ingredientService.saveOrUpdateIngredient(any(IngredientCommand.class))).thenReturn(Optional.of(value));

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));

    }
}