package com.edu.recipies.repository.reactive;

import com.edu.recipies.model.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CategoryReactiveRepository extends ReactiveCrudRepository<Category, String> {

    Mono<Category> findByDescription(String description);

}
