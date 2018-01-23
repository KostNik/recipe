package com.edu.recipies.service;

import com.edu.recipies.commands.CategoryCommand;
import com.edu.recipies.converters.toCommands.CategoryToCommand;
import com.edu.recipies.repository.reactive.CategoryReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryReactiveRepository repository;
    private CategoryToCommand          categoryToCommand;

    public CategoryServiceImpl(CategoryReactiveRepository repository, CategoryToCommand categoryToCommand) {
        this.repository = repository;
        this.categoryToCommand = categoryToCommand;
    }

    @Override
    public Flux<CategoryCommand> getAll() {
        return repository.findAll().map(categoryToCommand::convert);
    }

    @Override
    public Mono<CategoryCommand> getById(String id) {
        return repository.findById(id).map(categoryToCommand::convert);
    }
}
