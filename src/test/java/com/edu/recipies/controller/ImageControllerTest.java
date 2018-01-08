package com.edu.recipies.controller;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.service.ImageService;
import com.edu.recipies.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ImageControllerTest {


    @Mock
    private RecipeService recipeService;

    @Mock
    private ImageService imageService;

    private ImageController imageController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(recipeService, imageService);
    }

    @Test
    public void testGetImageForm() throws Exception {
        MockMvc build = MockMvcBuilders.standaloneSetup(imageController).build();

        when(recipeService.findCommandById(anyLong())).thenReturn(Optional.of(new RecipeCommand()));
        build.perform(get("/recipe/1/imageform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk());
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testHandleImageForm() throws Exception {
        MockMultipartFile image = new MockMultipartFile("imagefile", "testFile", "image", "Test".getBytes());


        MockMvc build = MockMvcBuilders.standaloneSetup(imageController).build();

        build.perform(multipart("/recipe/1/image").file(image))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());

    }

    @Test
    public void renderImageFromDB() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);

        Byte[] testArray = new Byte[]{12, 34, 56, 78, 2};
        recipeCommand.setImage(testArray);

        MockMvc build = MockMvcBuilders.standaloneSetup(imageController).build();
        when(recipeService.findCommandById(anyLong())).thenReturn(Optional.of(recipeCommand));


        MockHttpServletResponse response = build.perform(get("/recipe/3/image"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertEquals(response.getContentAsByteArray().length, testArray.length);
    }
}