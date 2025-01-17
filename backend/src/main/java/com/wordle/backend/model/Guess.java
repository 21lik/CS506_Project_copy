package com.wordle.backend.model;
/**
 * This File is used to handle a user guess
 */
import java.io.*;
import java.lang.IndexOutOfBoundsException;

public class Guess {

    // instance variables

    public enum LetterStatus {
        GREY,  // not in the word (grey)
        YELLOW,  // in word, wrong position (yellow)
        GREEN   // in word, correct position (green)
    }

    protected String guessedWord;
    protected LetterStatus[] letterStatuses;
    protected String guessedLettersStatuses;

    /**
     * Guess constructor
     * @author Annika Olson
     */
    public Guess(String guessedWord) {
        // TODO: put guess in database
        this.guessedWord = guessedWord;
        this.letterStatuses = new LetterStatus[guessedWord.length()];
        // initialize every letter to grey
        for (int i = 0; i < letterStatuses.length; i++) {
            letterStatuses[i] = LetterStatus.GREY;
        }
    }

    // getters

    /**
     * Guess constructor
     * @author Annika Olson
     * @return String guessed word
     */
    public String getGuessedWord() {
        return guessedWord;
    }

    /**
     * Get letter status of a single letter
     * @author Annika Olson
     * @param int index
     * @return char status
     * @throws IndexOutOfBoundsException if index is negative or greater than length of guessed word - 1
     */
    public LetterStatus getSingleLetterStatus(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > guessedWord.length() - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return letterStatuses[index];
        }
    }

    /**
     * Get all letter statuses
     * @author Annika Olson
     * @return char[] statuses
     */
    public LetterStatus[] getLetterStatuses() {
        return letterStatuses;
    }

    /**
     * Gets the length of the guessed word
     * @author Annika Olson
     * @return int length of of guessed word
     */
    public int getLengthOfGuessedWord() {
        return guessedWord.length();
    }

    /**
     * Set a letter's status
     * @author Annika Olson
     * @param int index
     * @param char status
     * @throws IndexOutOfBoundsException if index is negative or greater than size of guessed word
     */
    public void setLetterStatus(int index, LetterStatus status) throws IndexOutOfBoundsException {
        if (index >= 0 && index < guessedWord.length()) {
            letterStatuses[index] = status;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public String convert_guessed_letters() {
        String guessed_word_statuses = "";
        // TODO: make this conversion nicer and error check
        // convert each letter status to a character for frontend to process
        for (int i = 0; i < letterStatuses.length; i++) {
            if (letterStatuses[i] == LetterStatus.GREEN) {
                guessed_word_statuses += "G";
            } else if (letterStatuses[i] == LetterStatus.YELLOW) {
                guessed_word_statuses += "Y";
            } else if (letterStatuses[i] == LetterStatus.GREY) {
                guessed_word_statuses += "X";
            } else {    // NULL
                guessed_word_statuses += "";   // TODO: add error checking
            }
        }

        return guessed_word_statuses;
    }

}