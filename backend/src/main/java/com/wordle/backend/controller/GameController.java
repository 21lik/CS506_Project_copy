package com.wordle.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordle.backend.json.*;
import com.wordle.backend.model.Guess;
import com.wordle.backend.model.Word;
import com.wordle.backend.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GameController {
    ObjectMapper objMapper = new ObjectMapper();

    @Autowired
    private GameService gameService;

    // recieve word guess from frontend
    @CrossOrigin(origins = "https://localhost:3000")
    @PostMapping("/api/guess-word")
    public String guessWord(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        String todays_wordle = "zebra";
        // String todays_wordle = new Word().getWordle(); // TODO: debug, what if two tabs open at the same time, one start game before midnight and one after?
        String guessedWord = requestBody.get("guessedWord");
        Guess g1 = gameService.processGuess(guessedWord, todays_wordle);
        String guessLetters = g1.convert_guessed_letters();
        System.out.println(guessLetters);  // TODO: remove when done testing

        GuessWordResponseJson jsonObj = new GuessWordResponseJson(guessLetters);

        String jsonString = objMapper.writeValueAsString(jsonObj);
        System.out.println(jsonString);

        return jsonString;
    }

    // get new user from frontend
    @CrossOrigin(origins = "https://localhost:3000")
    @PutMapping("/api/new-user")
    public String newUser(@RequestBody Map<String, String> newUserRequest) throws JsonProcessingException {
        String email = "placeholder";
        String password = newUserRequest.get("password");
        String pass_val = newUserRequest.get("pass_val");
        String username = newUserRequest.get("username");

        System.out.println("creating new user: " + username); // TODO: remove when done testing

        // attempts user creation and returns feedback as follows
            // "passbad" - password invalid chars
            // "userbad" - username invalid chars
            // "passlong" - password is too long / empty
            // "userlong" - username is too long / empty 
            // "userdup" - username is already in use
            // "passmatch" - password and verify password don't match
            // "error" - some error occurred that prevented user creation
            // "success" - user creation succeeded
        String message = gameService.createUser(email, password, pass_val, username);
        NewUserResponseJson jsonObj = new NewUserResponseJson(message);

        String jsonString = objMapper.writeValueAsString(jsonObj);
        System.out.println(jsonString);

        return jsonString;
    }

    // login
    @CrossOrigin(origins = "https://localhost:3000")
    @GetMapping("/api/login")
    public String login(@RequestParam String username, @RequestParam String password) throws JsonProcessingException {
        System.out.printf("Logging in with username %s and password %s\n", username, password); // TODO: debug
        // returns 
            // "gamestart" when a new or previous game is to start
            // "gameover" when the game has been completed already
            // "loginfail" when the login doesn't work
        String message = gameService.loginService(password, username);
        LoginResponseJson jsonObj = new LoginResponseJson(message);

        String jsonString = objMapper.writeValueAsString(jsonObj);
        System.out.println(jsonString);

        return jsonString;
    }

    // update daily word when a new guest game is started
    @CrossOrigin(origins = "https://localhost:3000")
    @PutMapping("/api/guest")
    public String guestGame() throws JsonProcessingException {
        Word w = new Word();
        w.updateWord();

        // return value necessary to prevent frontend error
        boolean successful = true;
        GuestGameResponseJson jsonObj = new GuestGameResponseJson(successful);

        String jsonString = objMapper.writeValueAsString(jsonObj);
        System.out.println(jsonString);

        return jsonString;
    }
}
