package com.edu.recipies.controller;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.IngredientService;
import com.edu.recipies.service.RecipeService;
import com.edu.recipies.service.UnitOfMeasureService;
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
    public String showAllIngredients(@PathVariable Long id, Model model) {
        return recipeService.findCommandById(id).map(recipeCommand -> {
            model.addAttribute("recipe", recipeCommand);
            return "recipe/ingredient/list";
        }).orElse("redirect:/all");
    }


    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable(value = "ingredientId") Long ingrId, Model model) {
        return ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId)
                .map(ingredient -> {
                    model.addAttribute("ingredient", ingredient);
                    return "recipe/ingredient/show";
                }).orElse("redirect:/all");
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForm(@PathVariable Long recipeId, @PathVariable(value = "ingredientId") Long ingrId, Model model) {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId).orElse(new IngredientCommand());
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommands);

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredientForm(@PathVariable Long recipeId, Model model) {

        Optional<RecipeCommand> recipeOptional = recipeService.findCommandById(recipeId);
        if (!recipeOptional.isPresent()) {
            //todo need to handle it
        }
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

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
    public String saveIngredientCommand(@PathVariable Long recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        Optional<IngredientCommand> optionalCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand);
        return "redirect:/recipe/" + recipeId + "/ingredient/" + optionalCommand.get().getId() + "/show";
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public void saveIngredientCommand(@PathVariable Long recipeId, @PathVariable(value = "ingredientId") Long ingrId) {
        Boolean deleteIngredientResult = ingredientService.deleteIngredient(ingrId);
    }


}
