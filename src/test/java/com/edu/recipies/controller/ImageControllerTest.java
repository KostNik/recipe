package com.edu.recipies.controller;

import com.edu.recipies.commands.RecipeCommand;
import com.edu.recipies.service.ImageService;
import com.edu.recipies.service.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Ignore
public class ImageControllerTest {


    @Mock
    private RecipeService recipeService;

    @Mock
    private ImageService imageService;

    private ImageController imageController;
    private MockMvc         mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetImageForm() throws Exception {
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(new RecipeCommand()));
        mockMvc.perform(get("/recipe/1/imageform"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(status().isOk());
        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void testHandleImageForm() throws Exception {
        MockMultipartFile image = new MockMultipartFile("imagefile", "testFile", "image", "Test".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(image))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());

    }

//    @Test
//    @Ignore
//    public void testBadNumberFormatException() throws Exception {
//        mockMvc.perform(get("/recipe/someString/image"))
//                .andExpect(status().isBadRequest())
//                .andExpect(model().attributeExists("exception"))
//                .andExpect(view().name("400error"));
//    }

    @Test
    public void renderImageFromDB() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("3");

        Byte[] testArray = new Byte[]{12, 34, 56, 78, 2};
        recipeCommand.setImage(testArray);

        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

//        MockHttpServletResponse response = mockMvc.perform(get("/recipe/3/image"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//
//        assertEquals(response.getContentAsByteArray().length, testArray.length);
    }
}