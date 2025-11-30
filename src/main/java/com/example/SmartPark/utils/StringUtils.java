package com.example.SmartPark.utils;

public class StringUtils {

    public static String removeUnnecessaryWhiteSpaces(String text){
        return text.trim().replaceAll("\\s+", " ");
    }
}
