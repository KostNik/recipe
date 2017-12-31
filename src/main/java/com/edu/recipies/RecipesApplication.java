package com.edu.recipies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.edu"})
public class RecipesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RecipesApplication.class, args);

    }

}
