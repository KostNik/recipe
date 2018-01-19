package com.edu.recipies.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
public class Notes {

    private String id = UUID.randomUUID().toString();

    private String recipeNotes;

}
