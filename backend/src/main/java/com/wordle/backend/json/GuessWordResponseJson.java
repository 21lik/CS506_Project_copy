package com.wordle.backend.json;

public class GuessWordResponseJson {
    public String guessLetters;

    public GuessWordResponseJson(String guessLetters) {
        this.guessLetters = guessLetters;
    }
}
