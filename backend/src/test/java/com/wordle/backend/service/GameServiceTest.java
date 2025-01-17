import com.wordle.backend.model.Guess;
import com.wordle.backend.service.DictionaryService;
import com.wordle.backend.service.GameService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This file hosts JUnit tests for the processGuess method in 
 * the GameService class
 * @author Myranda Bischoff
 */
class GameServiceTest {

    private GameService gameService;
    private DictionaryService dictionaryService;
    private HttpSession session;
    /**
     * Setup method to run before each test
     * @author Myranda Bischoff
     */
    @BeforeEach
    void setUp() {
        // Mock the DictionaryService to control its behavior in tests
        dictionaryService = mock(DictionaryService.class);

        // Mock the session class
        session = mock(HttpSession.class);
        
        // Inject the mocked DictionaryService into GameService
        gameService = new GameService(dictionaryService, session);
    }

    /**
     * Test process guess with an exact match of today's wordle
     * @author Myranda Bischoff
     */
    @Test
    void testExactMatch() {
        // Test scenario where the guess exactly matches the word
        String todaysWordle = "apple";
        String currentGuess = "apple";

        // Mock the dictionary service to validate the guess as correct
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify all letters in the guess are marked as GREEN (correct position)
        for (Guess.LetterStatus status : result.getLetterStatuses()) {
            assertEquals(Guess.LetterStatus.GREEN, status);
        }
    }

    /**
     * Test process guess with a guess with no common letters
     * @author Myranda Bischoff
     */
    @Test
    void testAllLettersIncorrect() {
        // Test scenario where none of the guessed letters are in the target word
        String todaysWordle = "apple";
        String currentGuess = "words";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify all letters in the guess are marked as GREY (not in the word)
        for (Guess.LetterStatus status : result.getLetterStatuses()) {
            assertEquals(Guess.LetterStatus.GREY, status);
        }
    }

    /**
     * Test process guess with a guess with one letter off
     * @author Myranda Bischoff
     */
    @Test
    void testMixedMatch() {
        // Test scenario with a mix of GREEN and GREY statuses
        String todaysWordle = "apple";
        String currentGuess = "apply";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify that the first four letters match (GREEN) and the last letter does not (GREY)
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(0));
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(1));
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(2));
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(3));
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));
    }

    /**
     * Test process guess with a mix of misplaced letters and incorrect letters
     * @author Myranda Bischoff
     */
    @Test
    void testYellowAndGreyLetters() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "apple";
        String currentGuess = "lemon";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(0)); // 'l' in wrong place
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'e' in wrong place
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(2));   // 'm' not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'o' not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));   // 'n' not in word
    }

    /**
     * Test process guess with a mix of misplaced letters, correct letters, 
     * and incorrect letters
     * @author Myranda Bischoff
     */
    @Test
    void testYellowGreyAndGreenLetters() {
        // Test scenario with letters marked as YELLOW (misplaced) and GREY (not in word)
        String todaysWordle = "apple";
        String currentGuess = "plane";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Validate individual letter statuses
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(0)); // 'p' in wrong place
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'l' not in right place
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(2));  // 'a' not in wright place
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'n' not in word
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(4));   // 'e' is correct
    }

     /**
     * Test process guess with duplicate letters in the right place
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersRightPlace() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "poppy";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(0)); // 'h' not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(1));   // 'a' not in word
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(2));   // 'p' in right spot
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(3)); // 'p' in right spot
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(4));   // 'y' in right spot
    }

    /**
     * Test process guess with duplicate letters where the guess has duplicate letters
     * but one is in the wrong place
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersOneWrongPlace() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "apple";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(0)); // 'h' not in word
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'a' in wrong place
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(2));   // 'p' in right spot
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(3)); // 'p' in wrong spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));   // 'y' not in word
    }

    /**
     * Test process guess with no duplicate letters in world but duplicate letters in guess
     * where both duplicate letters are in the wrong spot
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersWrongPlace() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "level";
        String currentGuess = "scene";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(0)); // 's' not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(1));   // 'c' not in word
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(2));   // 'e' in wrong spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'n' not in word
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(4));   // 'e' in wrong spot
    }

    /**
     * Test process guess where todays wordle doesn't have duplicate letters but
     * the guess does and the first of the duplicate letters is in the right spot
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersInGuessOnlyWithFirstCorrect() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "maple";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(0)); // 'h' not in word
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(1));   // 'a' in right spot
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(2));   // 'p' in right spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'p' second p not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));   // 'y' not in word
    }

    /**
     * Test process guess where todays wordle doesn't have duplicate letters but
     * the guess does and the second of the duplicate letters is in the right spot
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersInGuessOnlyWithSecondCorrect() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "shape";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(0)); // 'h' in wrong spot
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'a' in wrong spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(2));   // 'p' second p not in word
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(3)); // 'p' in right spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));   // 'y' not in word
    }

    /**
     * Test process guess where todays wordle doesn't have duplicate letters but
     * the guess does where neither of the duplicate letters are in the right spot
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersInGuessOnlyNoneCorrect() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "place";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(0)); // 'h' not in word
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'a' in wrong spot
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(2));   // 'p' in wrong spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'p' second p not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(4));   // 'y' not in word
    }

    /**
     * Test process guess where the guess has duplicate letters that aren't in 
     * today's wordle
     * @author Myranda Bischoff
     */
    @Test
    void testDuplicateLettersNotInWordle() {
        // Test scenario with a mix of YELLOW (misplaced) and GREY (not in word) statuses
        String todaysWordle = "heavy";
        String currentGuess = "happy";

        // Mock the dictionary service to validate the guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(true);

        // Process the guess using GameService
        Guess result = gameService.processGuess(currentGuess, todaysWordle);

        // Verify letter statuses for this specific guess
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(0)); // 'h' in right spot
        assertEquals(Guess.LetterStatus.YELLOW, result.getSingleLetterStatus(1));   // 'a' in wrong spot
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(2));   // 'p' not in word
        assertEquals(Guess.LetterStatus.GREY, result.getSingleLetterStatus(3)); // 'p' not in word
        assertEquals(Guess.LetterStatus.GREEN, result.getSingleLetterStatus(4));   // 'y' in right spot
    }

    /**
     * Test process guess with an invalid guess (guess too short)
     * @author Myranda Bischoff
     */
    @Test
    void testInvalidGuess() {
        // Test scenario where the guess is not valid in the dictionary
        String todaysWordle = "apple";
        String currentGuess = "pear";

        // Mock the dictionary service to return false, indicating an invalid guess
        when(dictionaryService.isValidGuess(currentGuess)).thenReturn(false);

        // Verify that an exception is thrown for an invalid guess
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.processGuess(currentGuess, todaysWordle);
        });

        // Validate exception message
        assertEquals("Invalid guess", exception.getMessage());
    }
}
