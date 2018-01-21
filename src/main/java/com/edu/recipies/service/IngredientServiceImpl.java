package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.converters.fromCommands.CommandToIngredient;
import com.edu.recipies.converters.fromCommands.CommandToUnitOfMeasure;
import com.edu.recipies.converters.toCommands.IngredientToCommand;
import com.edu.recipies.model.Ingredient;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.model.UnitOfMeasure;
import com.edu.recipies.repository.reactive.RecipeReactiveRepository;
import com.edu.recipies.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToCommand             ingredientToCommandConverter;
    private final CommandToIngredient             commandToIngredientConverter;
    private final CommandToUnitOfMeasure          commandToUnitOfMeasure;
    private final RecipeReactiveRepository        recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToCommand ingredientToCommandConverter,
                                 CommandToIngredient commandToIngredientConverter,
                                 CommandToUnitOfMeasure commandToUnitOfMeasure,
                                 RecipeReactiveRepository recipeRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.ingredientToCommandConverter = ingredientToCommandConverter;
        this.commandToIngredientConverter = commandToIngredientConverter;
        this.commandToUnitOfMeasure = commandToUnitOfMeasure;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return findIngredientByRecipeIdAndIngredientId(recipeId, ingredientId)
                .map(ingredientToCommandConverter::convert)
                .map(ic -> {
                    ic.setRecipeId(recipeId);
                    return ic;
                });
    }

    private Mono<Ingredient> findIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(i -> i.getId().equalsIgnoreCase(ingredientId))
                .single();
    }

    @Override
    public Mono<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand) {

        String recipeId = ingredientCommand.getRecipeId();
        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId);

        if (recipeMono.block() == null) {
            log.error("Recipe isn't present");
            return Mono.just(new IngredientCommand());
        } else {
            Recipe recipe = recipeMono.block();
            String ingredientId = ingredientCommand.getId();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(i -> i.getId().equals(ingredientId)).findFirst();

            Ingredient ingredient;

            if (ingredientOptional.isPresent()) {
                ingredient = ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                UnitOfMeasureCommand unitOfMeasureCommand = ingredientCommand.getUnitOfMeasureCommand();
                if (unitOfMeasureCommand != null) {
                    UnitOfMeasure unitOfMeasure = unitOfMeasureRepository
                            .findById(unitOfMeasureCommand.getId())
                            .blockOptional()
                            .orElseGet(() -> unitOfMeasureRepository.save(commandToUnitOfMeasure.convert(unitOfMeasureCommand)).block());
                    ingredient.setUnitOfMeasure(unitOfMeasure);
                }
            } else {
                ingredient = commandToIngredientConverter.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
            }
            recipeRepository.save(recipe).block();
            return Mono.just(ingredient).map(ingredientToCommandConverter::convert);
        }
    }


    @Override
    public Boolean deleteIngredient(String recipeId, String ingredientId) {
        try {
            recipeRepository.findById(recipeId).subscribe(r -> {
                if (r.getIngredients().removeIf(ingredient -> ingredient.getId().equals(ingredientId))) {
                    recipeRepository.save(r).block();
                }
            });


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
