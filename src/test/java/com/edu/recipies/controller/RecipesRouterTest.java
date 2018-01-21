package com.edu.recipies.controller;

import com.edu.recipies.config.RoutesConfig;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

public class RecipesRouterTest {


    @Mock
    private RecipeService recipeService;

    private WebTestClient webTestClient;

    @Before
    public void seUp() {
        MockitoAnnotations.initMocks(this);
        RoutesConfig webConfig = new RoutesConfig();
        RouterFunction<?> routerFunction = webConfig.allRecipeRoute(recipeService);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void testGetRecipes() {
        Mockito.when(recipeService.getRecipes()).thenReturn(Flux.just());

        webTestClient.get()
                .uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }


    @Test
    public void testGetRecipesWithBody() {
        Mockito.when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));

        webTestClient.get()
                .uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Recipe.class);
    }


}
