package com.edu.recipies.repository;


import com.edu.recipies.model.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@DataJpaTest
@RunWith(SpringRunner.class)
public class UnitOfMeasureRepositoryIT {

    @Resource //or Autowire
    private UnitOfMeasureRepository repository;

    @Test
    public void testTeaspoon() {
        Optional<UnitOfMeasure> teaspoon = repository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", teaspoon.get().getDescription());
    }

}