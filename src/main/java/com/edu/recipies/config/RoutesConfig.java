package com.edu.recipies.config;

import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<?> allRecipeRoute(RecipeService recipeService) {
        return RouterFunctions.route(RequestPredicates.GET("/api/recipes"), response ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(recipeService.getRecipes(), Recipe.class));
    }

}
