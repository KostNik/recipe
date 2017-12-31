package com.edu.recipies.repository;

import com.edu.recipies.model.Category;
import com.edu.recipies.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
