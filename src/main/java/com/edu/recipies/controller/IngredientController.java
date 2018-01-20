package com.edu.recipies.controller;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.service.IngredientService;
import com.edu.recipies.service.RecipeService;
import com.edu.recipies.service.UnitOfMeasureService;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService        recipeService;
    private final IngredientService    ingredientService;
    private       UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping(value = "/recipe/{id}/ingredients")
    public String showAllIngredients(@PathVariable String id, Model model) {
        return recipeService.findCommandById(id)
                .blockOptional()
                .map(recipeCommand -> {
                    model.addAttribute("recipe", recipeCommand);
                    return "recipe/ingredient/list";
                }).orElse("redirect:/all");
    }


    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId, Model model) {
        Optional<IngredientCommand> ingredientOptional = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId).blockOptional();
        return ingredientOptional
                .map(ingredient -> {
                    model.addAttribute("ingredient", ingredient);
                    return "recipe/ingredient/show";
                }).orElse("redirect:/all");
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForm(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId, Model model) {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId).blockOptional().orElse(new IngredientCommand());
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = ImmutableSet.copyOf(unitOfMeasureService.listAllUoms().toIterable());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommands);

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredientForm(@PathVariable String recipeId, Model model) {

        Optional<RecipeCommand> recipeOptional = recipeService.findCommandById(recipeId).blockOptional();
        if (!recipeOptional.isPresent()) {
//            //todo need to handle it
        }
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = ImmutableSet.copyOf(unitOfMeasureService.listAllUoms().toIterable());

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setDescription("WWWWWW");
        ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);


        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommands);

        return "recipe/ingredient/ingredientform";
    }


    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveIngredientCommand(@PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand command = ingredientService.saveOrUpdateIngredient(ingredientCommand).block();
        return "redirect:/recipe/" + recipeId + "/ingredient/" + command.getId() + "/show";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public void saveIngredientCommand(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId) {
        Boolean deleteIngredientResult = ingredientService.deleteIngredient(recipeId, ingrId);
    }


}
