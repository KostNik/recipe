package com.edu.recipies.controller;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.exceptions.NotFoundException;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class RecipeController {

    private final static String RECIPE_FORM_URL = "recipe/recipeForm";

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping(value = "recipe/{id}/show", method = RequestMethod.GET)
    public String showRecipe(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.findById(id).block();
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @RequestMapping(value = "recipe/{id}/update", method = RequestMethod.GET)
    public String updateRecipe(@PathVariable String id, Model model) {
        recipeService.findCommandById(id).blockOptional().ifPresent(r -> model.addAttribute("recipe", r));
        return RECIPE_FORM_URL;
    }


    @RequestMapping(value = "recipe/new", method = RequestMethod.GET)
    public String newRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_FORM_URL;
    }


    @PostMapping("recipe")
    public String handleRecipeForm(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {

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
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName("404error");
        return modelAndView;
    }

}
