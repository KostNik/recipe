package com.edu.recipies.service;

import com.edu.recipies.commands.CategoryCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<CategoryCommand> getAll();

    Mono<CategoryCommand> getById(String id);
}
