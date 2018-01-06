package com.edu.recipies.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"description", "amount", "unitOfMeasure"})
public class Ingredient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String     description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(String description) {
        this.description = description;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.recipe = recipe;
        this.unitOfMeasure = unitOfMeasure;
    }


}
