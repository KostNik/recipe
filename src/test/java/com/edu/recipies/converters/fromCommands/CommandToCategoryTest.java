package com.edu.recipies.converters.fromCommands;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandToCategoryTest {

    private final static long   ID          = 1;
    private final static String DESCRIPTION = "new description";

    private CommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CommandToCategory();
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new CategoryCommand()));
    }



    @Test
    public void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setDescription(DESCRIPTION);
        command.setId(ID);

        Category converted = converter.convert(command);
        assertEquals(command.getId(), converted.getId());
        assertEquals(command.getDescription(), converted.getDescription());
    }
}