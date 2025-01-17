package com.wordle.backend.service;

import com.wordle.backend.model.Game;
import com.wordle.backend.model.Guess;
import com.wordle.backend.model.GameBoard;
import com.wordle.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import com.wordle.backend.model.Word;
import com.wordle.backend.model.NewUser;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import com.wordle.backend.model.Login;
import com.wordle.backend.model.PreGameCheck;
import com.wordle.backend.model.Streak;
import com.wordle.backend.model.Highscore;
import jakarta.servlet.http.HttpSession;

//import javax.servlet.http.HttpSession;
//TODO: If enough time I think player_id needs to be a httpsession attribute so that not every player has the same one
//TODO: make this class call word every 24 hours??? if enough time
@Service
public class GameService {

    private final DictionaryService dictionaryService;
    //private int player_id;
    //@Autowired
    private HttpSession session;

    @PostConstruct
    public void onStartup(){
        //might not be needed
    }


    /**
     * GameService Constructor
     * 
     * Constructor-based injection of DictionaryService
     * 
     * @author Myranda Bischoff
     * @param dictionaryService
     */
    @Autowired
    public GameService(DictionaryService dictionaryService, HttpSession session) {
        this.dictionaryService = dictionaryService;
        this.session = session;
    }

    /**
     * Used to create a new user
     * @param email users email
     * @param password users password
     * @param pass_val validate password
     * @param username users username
     * @return  "passbad" - password is too long / invalid chars
                "userbad" - username is too long / invalid chars
                "passlong" - password is too long / empty
                "userlong" - username is too long / empty
                "userdup" - username is already in use
                "passmatch" - password and verify password don't match
                "error" - some error occurred that prevented user creation
                "success" - user creation succeeded
     */
    public String createUser(String email, String password, String pass_val, String username) {
        Word word = new Word();
        word.updateWord();
        NewUser createUser = new NewUser();
        return createUser.createNewUser(username, password, pass_val, email); 
    }

    /**
     * logs user in 
     * @param password users password
     * @param username users username
     * @return returns "gamestart" when a new or previous game is to start
     *                 "gameover" when the game has been completed already
     *                 "loginfail" when the login doesn't work
     */
    public String loginService(String password, String username) {
        Login loginUser = new Login();
        boolean success = loginUser.tryLogin(username, password);
        if(success == true){
            int player_id = loginUser.getPlayerID();
            session.setAttribute("player_id", player_id);
            PreGameCheck preGameCheck = new PreGameCheck(player_id);  
            boolean canPlay = preGameCheck.startPreCheck();

            if(canPlay == true){
                //TODO: restore gamestate or start new game, this code below is just placeholder
                //NOTE: streak needs to be called if a NEW game is started also Word needs to as well (maybe)
                // Also a new entry in the game table needs to be made
                //GameStartAndRestore gamestart = new GameStartAndRestore();
                //gamestart.startGame(currGame);

                
                //TODO: Streak should be called when a new game starts, as of now we dont have a way to
                //store game state so new game is always started, if changed delete streak from here same for word
                Streak streak = new Streak(player_id);
                streak.streakCheck();
                Word word = new Word();
                word.updateWord();
                System.out.println(word.getWordle());
                System.out.println("Current Player ID: " + getPlayerID());
                return "gamestart";
            }
            else{
                return "gameover";
            }
        }
        else{
            return "loginfail";
        }
    }
    
    /**
     * Process the users guess. 
     * Index colors are set using an enumerated type.
     * 
     * Today's Word: [GREEN, GREEN, GREEN, GREEN, GREEN]
     * Invalid Guess: 
     * Otherwise at each index:
     *      Letter in correct index: GREEN
     *      Letter in word, but wrong index: YELLOW
     *      Letter not in word: GREY
     * 
     * @param current_guess the current guess
     * @param todays_wordle the word trying to be guessed
     * @return a guess with the current guess and correct stored values
     */
    public Guess processGuess(String current_guess, String todays_Wordle) {
        current_guess = current_guess.toLowerCase();
        todays_Wordle = todays_Wordle.toLowerCase();
        
        // Get game board instance
        GameBoard gameBoard = GameBoard.getInstance();

        // 1. Check if the current guess is valid
        if (!dictionaryService.isValidGuess(current_guess)) {
            throw new IllegalArgumentException("Invalid guess");
        } else {
            // Is a valid guess
            // Store guess in game board
            gameBoard.guessUpdateGameBoard(current_guess);

            // TODO: Store guess in database
        }

        // Make a guess object from the current guess
        Guess guess = new Guess(current_guess);

        // Track "used" letters in today's wordle (duplicate letter check)
        boolean[] used = new boolean[todays_Wordle.length()];

        // Step 2: Check for exact matches (GREEN)
        for (int i = 0; i < current_guess.length(); i++) {
            if (current_guess.charAt(i) == todays_Wordle.charAt(i)) {
                guess.setLetterStatus(i, Guess.LetterStatus.GREEN);
                used[i] = true;
            }
        }

        // Step 3: Check for misplaced letters (YELLOW)
        for (int i = 0; i < current_guess.length(); i++) {
            // Skip if already assigned GREEN
            if (guess.getSingleLetterStatus(i) == Guess.LetterStatus.GREEN) {
                continue; // Skip to the next index
            }

            // Check if the letter exists in today's Wordle but is not yet used
            for (int j = 0; j < todays_Wordle.length(); j++) {
                if (!used[j] && current_guess.charAt(i) == todays_Wordle.charAt(j)) {
                    guess.setLetterStatus(i, Guess.LetterStatus.YELLOW);
                    used[j] = true; // Mark this letter as used
                    break; // Stop checking once a match is found
                }
            }
        }

        // Step 4: Set remaining letters to GREY
        for (int i = 0; i < current_guess.length(); i++) {
            if (guess.getSingleLetterStatus(i) != Guess.LetterStatus.GREEN && 
                guess.getSingleLetterStatus(i) != Guess.LetterStatus.YELLOW) {
                guess.setLetterStatus(i, Guess.LetterStatus.GREY);
            }
        }

        return guess;
    }

    public int getPlayerID(){
        //return player_id;
        Integer playerId = (Integer) session.getAttribute("player_id");
        if (playerId == null) {
            throw new IllegalStateException("Player is not logged in");
        }
        return playerId;
    }
}