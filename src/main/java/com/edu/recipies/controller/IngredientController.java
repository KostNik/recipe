package com.edu.recipies.controller;

import com.edu.recipies.commands.IngredientCommand;
import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.service.IngredientService;
import com.edu.recipies.service.RecipeService;
import com.edu.recipies.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService        recipeService;
    private final IngredientService    ingredientService;
    private       UnitOfMeasureService unitOfMeasureService;
    private       WebDataBinder        webDataBinder;

    @Autowired
    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }


    @GetMapping
    @RequestMapping(value = "/recipe/{id}/ingredients")
    public String showAllIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }


    @GetMapping
    @RequestMapping(value = "/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredientForm(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId, Model model) {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingrId).blockOptional().orElse(new IngredientCommand());
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredientForm(@PathVariable String recipeId, Model model) {

        Optional<RecipeCommand> recipeOptional = recipeService.findCommandById(recipeId).blockOptional();
        if (!recipeOptional.isPresent()) {
//            //todo need to handle it
        }
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUnitOfMeasureCommand(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientform";
    }


    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveIngredientCommand(@PathVariable String recipeId,
                                        @ModelAttribute("ingredient") IngredientCommand ingredientCommand,
                                        Model model) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> log.info("ERROR {}", e));
            return "recipe/ingredient/ingredientform";
        }
        IngredientCommand command = ingredientService.saveOrUpdateIngredient(ingredientCommand).block();
        return "redirect:/recipe/" + recipeId + "/ingredient/" + command.getId() + "/show";
    }

    @ModelAttribute("uoms")
    public Flux<UnitOfMeasureCommand> populateUoms() {
        return unitOfMeasureService.listAllUoms();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public void saveIngredientCommand(@PathVariable String recipeId, @PathVariable(value = "ingredientId") String ingrId) {
        ingredientService.deleteIngredient(recipeId, ingrId);
    }


}
