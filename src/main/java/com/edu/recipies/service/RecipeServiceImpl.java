package com.edu.recipies.service;

import com.edu.recipies.converters.fromCommands.CommandToRecipe;
import com.edu.recipies.converters.toCommands.RecipeToCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.exceptions.NotFoundException;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import com.edu.recipies.repository.reactive.RecipeReactiveRepository;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final CommandToRecipe          commandToRecipe;
    private final RecipeToCommand          recipeToCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository,
                             CommandToRecipe commandToRecipe,
                             RecipeToCommand recipeToCommand) {
        this.recipeRepository = recipeRepository;
        this.commandToRecipe = commandToRecipe;
        this.recipeToCommand = recipeToCommand;
    }


    @Override
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = commandToRecipe.convert(recipeCommand);
        Mono<Recipe> saved = recipeRepository.save(recipe);
        return saved.map(recipeToCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeRepository.findById(id).map(recipeToCommand::convert);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }

}
