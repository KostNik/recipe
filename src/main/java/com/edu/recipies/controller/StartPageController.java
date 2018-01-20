package com.edu.recipies.controller;

import com.edu.recipies.service.RecipeService;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartPageController {

    private final RecipeService recipeService;

    public StartPageController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping(value = {"/all", "/"}, method = RequestMethod.GET)
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", ImmutableSet.copyOf(recipeService.getRecipes().toIterable()));
        return "index";
    }

}
