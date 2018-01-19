package com.edu.recipies.repository.reactive;

import com.edu.recipies.model.Recipe;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RecipeReactiveRepository extends ReactiveCrudRepository<Recipe, String> {
}
