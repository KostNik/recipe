package com.edu.recipies.service;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.converters.fromCommands.CommandToIngredient;
import com.edu.recipies.converters.fromCommands.CommandToUnitOfMeasure;
import com.edu.recipies.converters.toCommands.IngredientToCommand;
import com.edu.recipies.model.Ingredient;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.model.UnitOfMeasure;
import com.edu.recipies.repository.IngredientRepository;
import com.edu.recipies.repository.RecipeRepository;
import com.edu.recipies.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository    ingredientRepository;
    private final IngredientToCommand     ingredientToCommandConverter;
    private final CommandToIngredient     commandToIngredientConverter;
    private final CommandToUnitOfMeasure  commandToUnitOfMeasure;
    private final RecipeRepository        recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientToCommand ingredientToCommandConverter,
                                 CommandToIngredient commandToIngredientConverter,
                                 CommandToUnitOfMeasure commandToUnitOfMeasure,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToCommandConverter = ingredientToCommandConverter;
        this.commandToIngredientConverter = commandToIngredientConverter;
        this.commandToUnitOfMeasure = commandToUnitOfMeasure;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Optional<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId).map(ingredientToCommandConverter::convert);
    }

    @Override
    public Optional<IngredientCommand> saveOrUpdateIngredient(IngredientCommand ingredientCommand) {

        String recipeId = ingredientCommand.getRecipeId();
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe isn't present");
            return Optional.of(new IngredientCommand());
        } else {
            Recipe recipe = recipeOptional.get();
            String ingredientId = ingredientCommand.getId();
            Optional<Ingredient> ingredientOptional = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId);

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                UnitOfMeasureCommand unitOfMeasureCommand = ingredientCommand.getUnitOfMeasureCommand();
                if (unitOfMeasureCommand != null) {
                    UnitOfMeasure unitOfMeasure = unitOfMeasureRepository
                            .findById(unitOfMeasureCommand.getId())
                            .orElseGet(() -> unitOfMeasureRepository.save(commandToUnitOfMeasure.convert(unitOfMeasureCommand)));
                    ingredient.setUnitOfMeasure(unitOfMeasure);
                }
            } else {
                recipe.addIngredient(commandToIngredientConverter.convert(ingredientCommand));
            }
            Recipe saved = recipeRepository.save(recipe);
            if (ingredientId == null) {
                return saved.getIngredients().stream()
                        .map(ingredientToCommandConverter::convert)
                        .filter(ingredientCommand::equals)
                        .findFirst();
            } else {
                return saved.getIngredients().stream()
                        .filter(i -> i.getId().equals(ingredientId))
                        .findFirst().map(ingredientToCommandConverter::convert);
            }
        }
    }

    @Override
    public Boolean deleteIngredient(String id) {
        try {
            ingredientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
