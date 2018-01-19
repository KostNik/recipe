package com.edu.recipies.repository;


import com.edu.recipies.BootstrapApp;
import com.edu.recipies.model.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@DataMongoTest
@RunWith(SpringRunner.class)
public class UnitOfMeasureRepositoryIT {

    @Autowired //or Autowire
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    private RecipeRepository        recipeRepository;
    @Autowired
    private CategoryRepository      categoryRepository;

    @Before
    public void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();


        BootstrapApp bootstrapApp = new BootstrapApp(recipeRepository, unitOfMeasureRepository, categoryRepository);
        bootstrapApp.onApplicationEvent();
    }


    @Test
    public void testTeaspoon() {
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", teaspoon.get().getDescription());
    }

    @Test
    public void testCup() {
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", cup.get().getDescription());
    }

}