package com.edu.recipies.service;

import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {this.recipeRepository = recipeRepository;}


    @Override
    public Set<Recipe> getRecipes() {
        return ImmutableSet.copyOf(recipeRepository.findAll());
    }
}
