package com.edu.recipies;

import java.util.Collection;

public class RecipeUtils {


    public static boolean isValidCollection(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
}
