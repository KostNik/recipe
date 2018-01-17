package com.edu.recipies.controller;

import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class StartPageControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private StartPageController startPageController;
    private MockMvc             mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        startPageController = new StartPageController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(startPageController).build();
    }

    @Test
    public void testMockMVC() throws Exception {
        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipes.add(recipe);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        when(recipeService.getRecipes()).thenReturn(recipes);

        assertEquals("index", startPageController.getAllRecipes(model));

        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeService, times(1)).getRecipes();
        assertEquals(2, argumentCaptor.getValue().size());
    }
}