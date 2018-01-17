package com.edu.recipies.controller;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.service.ImageService;
import com.edu.recipies.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.edu.recipies.RecipeUtils.convertToBytePrimitiveArray;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService  imageService;

    private String defaultRecipeImage;


    public ImageController(RecipeService recipeService,
                           ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @PostConstruct
    public void postInit() {
        defaultRecipeImage = "static/images/1495691699167923625.jpg";
    }

    @GetMapping("/recipe/{id}/imageform")
    public String getImageForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id).get());
        return "recipe/imageuploadform";
    }

    @GetMapping("/recipe/{id}/image")
    public void getRecipeImage(@PathVariable String id, HttpServletResponse response) throws IOException, URISyntaxException {
        RecipeCommand recipeCommand = recipeService.findCommandById(id).get();
        Byte[] imageDataFromDB = recipeCommand.getImage();
        byte[] imageBytes;
        if (imageDataFromDB == null || imageDataFromDB.length == 0) {
            imageBytes = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader().getResource(defaultRecipeImage).toURI()));
        } else {
            imageBytes = convertToBytePrimitiveArray(imageDataFromDB);
        }
        response.setContentType("image/jpeg");
        IOUtils.copy(new ByteArrayInputStream(imageBytes), response.getOutputStream());
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImageForm(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        imageService.saveImageFile(id, multipartFile);
        return "redirect:/recipe/" + id + "/show";
    }


}
