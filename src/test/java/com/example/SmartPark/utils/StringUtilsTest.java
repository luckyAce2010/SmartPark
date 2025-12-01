package com.example.SmartPark.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void removesLeadingAndTrailingSpaces() {
        String input = "   hello world   ";
        String expected = "hello world";
        assertEquals(expected, StringUtils.removeUnnecessaryWhiteSpaces(input));
    }

    @Test
    void replacesMultipleSpacesWithSingleSpace() {
        String input = "hello    world   from   java";
        String expected = "hello world from java";
        assertEquals(expected, StringUtils.removeUnnecessaryWhiteSpaces(input));
    }

    @Test
    void returnsEmptyStringWhenInputIsEmpty() {
        String input = "";
        String expected = "";
        assertEquals(expected, StringUtils.removeUnnecessaryWhiteSpaces(input));
    }

    @Test
    void returnsSingleSpaceForSpacesOnlyInput() {
        String input = "     ";
        String expected = "";
        assertEquals(expected, StringUtils.removeUnnecessaryWhiteSpaces(input));
    }

    @Test
    void handlesSingleWordWithoutSpaces() {
        String input = "Hello";
        String expected = "Hello";
        assertEquals(expected, StringUtils.removeUnnecessaryWhiteSpaces(input));
    }
}