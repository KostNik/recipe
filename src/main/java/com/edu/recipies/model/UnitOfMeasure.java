package com.edu.recipies.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document
public class UnitOfMeasure {

    @Id
    private String id;

    private String description;

}
