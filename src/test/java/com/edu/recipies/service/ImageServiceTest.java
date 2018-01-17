package com.edu.recipies.service;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.model.Recipe;
import com.edu.recipies.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {


    private ImageService imageService;


    @Mock
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeService, recipeRepository);
    }

    @Test
    public void testSaveImageFile() throws IOException {
        Recipe recipe = new Recipe();
        recipe.setId("3L");

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        MockMultipartFile multipartFile = new MockMultipartFile("testImage", "testFile", "imageContent", "Test".getBytes());

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        imageService.saveImageFile("3L", multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe saved = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, saved.getImage().length);
    }
}