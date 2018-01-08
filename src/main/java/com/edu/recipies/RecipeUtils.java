package com.edu.recipies;

import java.util.Collection;

public class RecipeUtils {


    public static boolean isValidCollection(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static Byte[] convertToByteObjectsArray(byte[] source) {
        int length = source.length;
        Byte[] result = new Byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = source[i];
        }
        return result;
    }

    public static byte[] convertToBytePrimitiveArray(Byte[] source) {
        int length = source.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = source[i];
        }
        return result;
    }
}
