package com.edu.recipies.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Category {

    private String id;

    private String description;

    private Set<Recipe> recipes = new HashSet<>();

}
