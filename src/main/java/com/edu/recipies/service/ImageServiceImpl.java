package com.edu.recipies.service;

import com.edu.recipies.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.edu.recipies.RecipeUtils.convertToByteObjectsArray;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeService    recipeService;
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeService recipeService,
                            RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile multipartFile) {
        recipeRepository.findById(id).ifPresent(recipeCommand -> {
            try {
                recipeCommand.setImage(convertToByteObjectsArray(multipartFile.getBytes()));
                recipeRepository.save(recipeCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
