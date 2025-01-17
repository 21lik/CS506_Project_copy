package com.wordle.backend.model;
import java.util.Arrays;

public class GameBoard {
    /*
     * Instance variables
     */
    protected String[] current_game_guesses;
    private static GameBoard instance; // Singleton instance

    /**
     * GameBoard constructor
     * @author Myranda Bischoff
     */
    public GameBoard() {
        this.current_game_guesses = new String[6];
    }

    /**
     * Method to retrieve the single GameBoard instance.
     * This allows for the same instance to be used across classes.
     */
    public static GameBoard getInstance() {
        if (instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    /**
     * Update GameBoard based with data from database after a valid login.
     * NOTE: String array can be empty
     * @author Myranda Bischoff
     * 
     * @param previous_login_guesses string array from the database of previous guesses
     */
    public void loginUpdateGameBoard(String[] previous_login_guesses){
        // Make sure array is clear
        reset();

        // Prevent ArrayIndexOutOfBoundsException
        int length = Math.min(previous_login_guesses.length, 6);

        // Fill array with data from database
        System.arraycopy(previous_login_guesses, 0, this.current_game_guesses, 0, length);
    }

    /**
     * Update GameBoard based on new guess from frontend
     * @author Myranda Bischoff
     * 
     * @param new_guess a valid new guess to add to game board
     */
    public void guessUpdateGameBoard(String new_guess){
        // Add new guess to first null spot
        for (int i = 0; i < current_game_guesses.length; i++) {
            if (current_game_guesses[i] == null) {
                current_game_guesses[i] = new_guess;
                return;
            }
        }
    }

    /**
     * Get current game guesses
     * // TODO: ADD RETURN TAG
     */
    public String[] getCurrentGameGuesses(){
        return this.current_game_guesses;
    }

    /**
     * Reset the current instance
     */
    public void reset(){
        this.current_game_guesses = new String[6];
    }
}