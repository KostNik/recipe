package com.edu.recipies.service;

import com.edu.recipies.converters.fromCommands.CommandToRecipe;
import com.edu.recipies.converters.toCommands.RecipeToCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CommandToRecipe  commandToRecipe;
    private final RecipeToCommand  recipeToCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             CommandToRecipe commandToRecipe,
                             RecipeToCommand recipeToCommand) {
        this.recipeRepository = recipeRepository;
        this.commandToRecipe = commandToRecipe;
        this.recipeToCommand = recipeToCommand;
    }


    @Override
    public Set<Recipe> getRecipes() {
        return ImmutableSet.copyOf(recipeRepository.findAll());
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Optional<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = commandToRecipe.convert(recipeCommand);
        Recipe saved = recipeRepository.save(recipe);
        RecipeCommand savedRecipeCommand = recipeToCommand.convert(saved);
        return Optional.ofNullable(savedRecipeCommand);
    }

    @Override
    public Optional<RecipeCommand> findCommandById(Long id) {
        return recipeRepository.findById(id).map(recipeToCommand::convert);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
