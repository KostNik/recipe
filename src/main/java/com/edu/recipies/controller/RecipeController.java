package com.edu.recipies.controller;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.exceptions.NotFoundException;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.CategoryService;
import com.edu.recipies.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Controller
public class RecipeController {

    private final static String RECIPE_FORM_URL = "recipe/recipeForm";

    private final RecipeService   recipeService;
    private final CategoryService categoryService;
    private       WebDataBinder   webDataBinder;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @InitBinder("recipe")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @RequestMapping(value = "recipe/{id}/show", method = RequestMethod.GET)
    public String showRecipe(@PathVariable String id, Model model) {
        Mono<Recipe> recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @RequestMapping(value = "recipe/{id}/update", method = RequestMethod.GET)
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id).block());
        model.addAttribute("categories", categoryService.getAll());
        return RECIPE_FORM_URL;
    }


    @RequestMapping(value = "recipe/new", method = RequestMethod.GET)
    public String newRecipeForm(Model model) {
        model.addAttribute("recipe", Mono.just(new RecipeCommand()));
        model.addAttribute("categories", categoryService.getAll());
        return RECIPE_FORM_URL;
    }


    @PostMapping("recipe")
    public String handleRecipeForm(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> {
                log.info("ERROR {}", e);
            });
            return RECIPE_FORM_URL;
        }
        Optional<RecipeCommand> recipeCommandSaved = recipeService.saveRecipeCommand(recipeCommand).blockOptional();
        return recipeCommandSaved
                .map(command -> "redirect:/recipe/" + command.getId() + "/show")
                .orElse(RECIPE_FORM_URL);
    }


    @RequestMapping(value = "recipe/delete", method = RequestMethod.GET)
    public String deleteRecipe(@RequestParam String id) {
        recipeService.deleteById(id);
        return "redirect:/all";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "404error";
    }

}
