package com.wordle.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import java.io.IOException;

import com.wordle.backend.service.GameService;
import com.wordle.backend.service.DictionaryService;

public class DictionaryServiceTest {
    /**
     * Tests for dictionary service constructor
     */
    @Test 
    public void testDictionaryServiceConstructor() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();
        
        // Validate that it's been read in by checking a sample word 
        // that is in the dictionary
        assertTrue(dictionaryTest.contains("apple"));
    }

    @Test 
    public void testLoadDictionary() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();

        // Validate that it's been read in by checking a sample word 
        // that is in the dictionary
        assertTrue(dictionaryTest.contains("apple"));
    }

    /**
     * Tests for checking if a word is in the dictionary
     */
    @Test 
    public void testContainsWithInvalidWord() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();

        // Check that an invalid word is not in the dictionary
        assertFalse(dictionaryTest.contains("zzzzz"));
    }

    @Test 
    public void testContainsWithValidWordCaseSensitive() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();

        // Check if a valid but all caps word is in the dictionary
        assertTrue(dictionaryTest.contains("APPLE"));
    }

    @Test 
    public void testContainsWithValidWord() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();

        // Check if a valid word is in the dictionary
        assertTrue(dictionaryTest.contains("apple"));
    }

    /**
     * Tests for checking if a given guess is valid
     */
    @Test 
    public void testIsValidGuessInvalidLength() throws IOException {
        // Make Guess object with a valid dictionary path
        DictionaryService dictionaryTest = new DictionaryService();
        dictionaryTest.initializeDictionary();

        // Invalid word, length too long
        assertFalse(dictionaryTest.contains("apples"));

        // Invalid word, length too short
        assertFalse(dictionaryTest.contains("app"));
    }
}