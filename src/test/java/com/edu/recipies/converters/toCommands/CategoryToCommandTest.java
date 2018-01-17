package com.edu.recipies.converters.toCommands;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCommandTest {

    private final static String ID          = "1";
    private final static String DESCRIPTION = "new description";

    private CategoryToCommand converter;

    @Before
    public void setUp() {
        converter = new CategoryToCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() {
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        CategoryCommand converted = converter.convert(category);

        assertEquals(category.getId(), converted.getId());
        assertEquals(category.getDescription(), converted.getDescription());

    }
}