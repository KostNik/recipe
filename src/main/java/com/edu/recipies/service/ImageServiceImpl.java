package com.edu.recipies.service;

import com.edu.recipies.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.edu.recipies.RecipeUtils.convertToByteObjectsArray;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeService            recipeService;
    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeService recipeService,
                            RecipeReactiveRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String id, MultipartFile multipartFile) {
        recipeRepository.findById(id).subscribe(recipe -> {
            try {
                recipe.setImage(convertToByteObjectsArray(multipartFile.getBytes()));
                recipeRepository.save(recipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
