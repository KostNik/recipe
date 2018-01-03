package com.edu.recipies.controller;

import com.edu.recipies.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("recipe/show/{id}")
    public String showRecipe(@PathVariable long id, Model model) {
        recipeService.findById(id).ifPresent(r -> model.addAttribute("recipe", r));
        return "recipe/show";
    }
}
