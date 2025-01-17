package com.wordle.backend.service;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import com.wordle.backend.model.GameBoard;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class DictionaryService {
    private HashSet<String> dictionary_words;

    @PostConstruct
    /**
     * Create and load in the dictionary
     * @author Myranda Bischoff
     * 
     * @param file_path path to the dictionary file
     */
    public void initializeDictionary() throws IOException, FileNotFoundException {
        String file_path = "/Dictionary/5WordDict.txt";
        dictionary_words = new HashSet<>();
        loadDictionary(file_path);
    }

    /**
     * Read in Dictionary File
     * @author Myranda Bischoff
     * 
     * @param file_path path to the dictionary file
     */
    private void loadDictionary(String file_path) throws IOException, FileNotFoundException {
        try (InputStream inputStream = getClass().getResourceAsStream(file_path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        
            if (inputStream == null) {
                throw new FileNotFoundException("Dictionary file not found: " + file_path);
            }

            String word;
            while ((word = reader.readLine()) != null) {
                dictionary_words.add(word.trim().toLowerCase());
            }
            reader.close();
        }
    }

    /**
     * Check that a word is in the dictionary
     * @author Myranda Bischoff
     * 
     * @param guess the current guess
     */
    public boolean contains(String guess) {
        // Make all letters lowercase for case-insensitive comparison
        return dictionary_words.contains(guess.toLowerCase());
    }

    /**
     * Word and Letter Check
     * 
     * @param guess the current guess
     * @return true if the current guess is valid
     * @return false if the current guess is invalid
     */
    public boolean isValidGuess(String guess) {
        // Check that length of the guess is 5
        if (guess.length() != 5) {
            // length is <5 or >5
            return false;
        }

        // Check that the word guessed hasn't been previously guessed
        GameBoard gameBoard = GameBoard.getInstance(); //TODO: FIX NAMING CONVENTIONS
        String[] previous_guesses = gameBoard.getCurrentGameGuesses();

        for (int i = 0; i < previous_guesses.length; i++) {
            if (previous_guesses[i] != null && previous_guesses[i].equals(guess)) {
                // The guess has already been made
                return false;
            }
        }

        // Check that the word guessed is in the dictionary
        if (!contains(guess)) {
            // word not in dictionary
            return false;
        }

        // word is of valid length and in the dictionary
        return true;
    }
}
