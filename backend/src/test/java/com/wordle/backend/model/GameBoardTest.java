/**
 * This file hosts JUnit tests for the GameBoard class
 * @author Myranda Bischoff
 */
import com.wordle.backend.model.GameBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;

public class GameBoardTest {
    private GameBoard gameBoard;

    /**
     * Setup method to run before each test
     * @author Myranda Bischoff
     */
    
    @BeforeEach
    void setUp() {
        gameBoard = GameBoard.getInstance();
        gameBoard.reset(); // clear board for each test
    }

    /**
     * Test singleton 
     * @author Myranda Bischoff
     */
    @Test
    void testSingletonInstance() {
        GameBoard gameBoard1 = GameBoard.getInstance();
        GameBoard gameBoard2 = GameBoard.getInstance();
        
        // Both references point to the same instance
        assertSame(gameBoard1, gameBoard2, "GameBoard instances should be the same");
    }

    /**
     * Test login update functionality (check if previous guesses are set correctly)
     * @author Myranda Bischoff
     */ 
    @Test
    void testLoginUpdateGameBoard() {
        String[] previousGuesses = {"apple", "peach", "grape"};
        gameBoard.loginUpdateGameBoard(previousGuesses);

        // First 3 guesses are set and the rest are null
        String[] currentGuesses = gameBoard.getCurrentGameGuesses();
        assertEquals("apple", currentGuesses[0]);
        assertEquals("peach", currentGuesses[1]);
        assertEquals("grape", currentGuesses[2]);
        assertNull(currentGuesses[3]);
        assertNull(currentGuesses[4]);
        assertNull(currentGuesses[5]);
    }

    /**
     * Test guess update functionality
     * @author Myranda Bischoff
     */
    @Test
    void testGuessUpdateGameBoard() {
        // Initially, there should be no guesses
        String[] currentGuesses = gameBoard.getCurrentGameGuesses();
        assertNull(currentGuesses[0]);

        // Add a new guess
        gameBoard.guessUpdateGameBoard("apple");

        // New guess is added to the first position
        assertEquals("apple", currentGuesses[0]);
    }

    /**
     * Test guess update when there are multiple guesses
     * @author Myranda Bischoff
     */
    @Test
    void testGuessUpdateGameBoardMultipleGuesses() {
        gameBoard.guessUpdateGameBoard("apple");
        gameBoard.guessUpdateGameBoard("peach");
        
        String[] currentGuesses = gameBoard.getCurrentGameGuesses();
        assertEquals("apple", currentGuesses[0]);
        assertEquals("peach", currentGuesses[1]);
    }

    /**
     * Test guess update until all slots are filled
     * @author Myranda Bischoff
     */
    @Test
    void testGuessUpdateGameBoardFull() {
        // Fill all the spots with valid 5-letter guesses
        gameBoard.guessUpdateGameBoard("apple");
        gameBoard.guessUpdateGameBoard("peach");
        gameBoard.guessUpdateGameBoard("grape");
        gameBoard.guessUpdateGameBoard("lemon");
        gameBoard.guessUpdateGameBoard("mango");
        gameBoard.guessUpdateGameBoard("plums");

        // Check that the array has all guesses
        String[] currentGuesses = gameBoard.getCurrentGameGuesses();
        assertEquals("apple", currentGuesses[0]);
        assertEquals("peach", currentGuesses[1]);
        assertEquals("grape", currentGuesses[2]);
        assertEquals("lemon", currentGuesses[3]);
        assertEquals("mango", currentGuesses[4]);
        assertEquals("plums", currentGuesses[5]);
    }

    /**
     * Test login update with an empty array
     * @author Myranda Bischoff
     */
    @Test
    void testLoginUpdateGameBoardEmptyArray() {
        // Add some guesses before calling loginUpdateGameBoard
        gameBoard.guessUpdateGameBoard("apple");
        gameBoard.guessUpdateGameBoard("peach");
        gameBoard.guessUpdateGameBoard("grape");

        // Current guesses before the update
        String[] currentGuessesBefore = gameBoard.getCurrentGameGuesses();
        assertEquals("apple", currentGuessesBefore[0]);
        assertEquals("peach", currentGuessesBefore[1]);
        assertEquals("grape", currentGuessesBefore[2]);

        // Call loginUpdateGameBoard
        String[] emptyGuesses = {};
        gameBoard.loginUpdateGameBoard(emptyGuesses);

        // Assert that the guesses have been cleared
        String[] currentGuessesAfter = gameBoard.getCurrentGameGuesses();
        assertNull(currentGuessesAfter[0]);
        assertNull(currentGuessesAfter[1]);
        assertNull(currentGuessesAfter[2]);
        assertNull(currentGuessesAfter[3]);
        assertNull(currentGuessesAfter[4]);
        assertNull(currentGuessesAfter[5]);
    }

    /**
     * Test login update with partially filled array
     * @author Myranda Bischoff
     */
    @Test
    void testLoginUpdateGameBoardPartiallyFilledArray() {
        // Add some guesses before calling loginUpdateGameBoard
        gameBoard.guessUpdateGameBoard("apple");
        gameBoard.guessUpdateGameBoard("peach");
        gameBoard.guessUpdateGameBoard("grape");
        gameBoard.guessUpdateGameBoard("lemon");
        gameBoard.guessUpdateGameBoard("mango");
        gameBoard.guessUpdateGameBoard("plums");

        // Current guesses before the update
        String[] currentGuessesBefore = gameBoard.getCurrentGameGuesses();
        assertEquals("apple", currentGuessesBefore[0]);
        assertEquals("peach", currentGuessesBefore[1]);
        assertEquals("grape", currentGuessesBefore[2]);
        assertEquals("lemon", currentGuessesBefore[3]);
        assertEquals("mango", currentGuessesBefore[4]);
        assertEquals("plums", currentGuessesBefore[5]);

        // Call loginUpdateGameBoard
        String[] partiallyFilledGuesses = {"guava", "olive"};
        gameBoard.loginUpdateGameBoard(partiallyFilledGuesses);

        // Assert that the guesses have been cleared
        String[] currentGuessesAfter = gameBoard.getCurrentGameGuesses();
        assertEquals("guava", currentGuessesAfter[0]);
        assertEquals("olive", currentGuessesAfter[1]);
        assertNull(currentGuessesAfter[2]);
        assertNull(currentGuessesAfter[3]);
        assertNull(currentGuessesAfter[4]);
        assertNull(currentGuessesAfter[5]);
    }

    /**
     * Test login update with more than 6 guesses
     * @author Myranda Bischoff
     */
    @Test
    void testLoginUpdateGameBoardTooManyGuesses() {
        String[] tooManyGuesses = {"apple", "peach", "grape", "lemon", "mango", "plums", "berry"};

        // Call loginUpdateGameBoard with too many guesses
        gameBoard.loginUpdateGameBoard(tooManyGuesses);

        // Check that the length is exactly 6
        String[] currentGuesses = gameBoard.getCurrentGameGuesses();
        assertEquals(6, currentGuesses.length);
        
        // Check that the first 6 guesses were stored
        assertEquals("apple", currentGuesses[0]);
        assertEquals("peach", currentGuesses[1]);
        assertEquals("grape", currentGuesses[2]);
        assertEquals("lemon", currentGuesses[3]);
        assertEquals("mango", currentGuesses[4]);
        assertEquals("plums", currentGuesses[5]);
    }
}