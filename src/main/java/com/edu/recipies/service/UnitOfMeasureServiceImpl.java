package com.edu.recipies.service;

import com.edu.recipies.commands.UnitOfMeasureCommand;
import com.edu.recipies.converters.toCommands.UnitOfMeasureToCommand;
import com.edu.recipies.repository.UnitOfMeasureRepository;
import com.edu.recipies.repository.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {


    private final UnitOfMeasureReactiveRepository repository;
    private final UnitOfMeasureToCommand          unitOfMeasureToCommandConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository repository,
                                    UnitOfMeasureToCommand converter) {
        this.repository = repository;
        this.unitOfMeasureToCommandConverter = converter;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return repository.findAll()
                .map(unitOfMeasureToCommandConverter::convert);
    }
}
