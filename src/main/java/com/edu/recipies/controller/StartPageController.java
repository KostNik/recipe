package com.edu.recipies.controller;

import com.edu.recipies.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartPageController {


    public StartPageController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    private final RecipeService recipeService;


    @RequestMapping(value = {"/all", "/"}, method = RequestMethod.GET)
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

}
