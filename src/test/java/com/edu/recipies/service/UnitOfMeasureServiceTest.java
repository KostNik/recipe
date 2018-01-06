package com.edu.recipies.service;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.converters.toCommands.UnitOfMeasureToCommand;
import com.edu.recipies.model.UnitOfMeasure;
import com.edu.recipies.repository.UnitOfMeasureRepository;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceTest {

    @Mock
    private UnitOfMeasureRepository repository;

    private UnitOfMeasureService unitOfMeasureService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(repository, new UnitOfMeasureToCommand());
    }

    @Test
    public void testListAllUoms() {
        UnitOfMeasure unitOfMeasure_1 = new UnitOfMeasure();
        unitOfMeasure_1.setId(1L);
        UnitOfMeasure unitOfMeasure_2 = new UnitOfMeasure();
        unitOfMeasure_2.setId(2L);
        Set<UnitOfMeasure> unitOfMeasures = Sets.newHashSet(unitOfMeasure_1, unitOfMeasure_2);
        when(repository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        assertEquals(2, unitOfMeasureCommands.size());
        verify(repository, times(1)).findAll();

    }
}