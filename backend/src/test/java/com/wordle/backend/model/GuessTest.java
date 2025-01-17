/**
 * This file hosts JUnit tests for the Guess class
 * @author Annika Olson
 */

import com.wordle.backend.model.Guess;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.lang.IndexOutOfBoundsException;

public class GuessTest {

    /**
     * @author Annika Olson
     * Tests that when a new guess is created, all the letters initially
     * have their statuses set to grey (X)
     */
    @Test 
    public void testLetterStatusesInitializedToGreyWhenGuessCreated() {
        String word = "hello";
        Guess g1 = new Guess(word);
        Guess.LetterStatus[] expected = {Guess.LetterStatus.GREY, Guess.LetterStatus.GREY, Guess.LetterStatus.GREY, Guess.LetterStatus.GREY, Guess.LetterStatus.GREY};
        assertArrayEquals(expected, g1.getLetterStatuses());
    }

    /**
     * @author Annika Olson
     * Tests getGuessedWord() returns the guessed word
     */
    @Test 
    public void testGetGuessedWordReturnsGuessedWord() {
        String word = "hello";
        Guess g1 = new Guess(word);
        assertEquals(word, g1.getGuessedWord());
    }

    /**
     * @author Annika Olson
     * Tests that getSingleLetterStatus() throws IndexOutOfBoundsException when index
     * is out of bounds.
     */
    @Test 
    public void testGetSingleLetterStatusAtOutOfBoundsThrowsException() {
        String word = "hello";
        Guess g1 = new Guess(word);
        assertThrows(IndexOutOfBoundsException.class, () -> g1.getSingleLetterStatus(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> g1.getSingleLetterStatus(5));
    }

    /**
     * @author Annika Olson
     * Tests that get single letter status at a given index returns the correct status
     */
    @Test 
    public void testGetSingleLetterStatusAtIndex() {
        String word = "hello";
        Guess g1 = new Guess(word);
        assertEquals(Guess.LetterStatus.GREY, g1.getSingleLetterStatus(1));
    }

    /**
     * @author Annika Olson
     * Tests that the correct letter statuses are in the array
     */
    @Test 
    public void testGetLetterStatuses() {
        String word = "hello";
        Guess g1 = new Guess(word);
        g1.setLetterStatus(0, Guess.LetterStatus.GREEN);
        g1.setLetterStatus(2, Guess.LetterStatus.YELLOW);
        Guess.LetterStatus[] expected = {Guess.LetterStatus.GREEN, Guess.LetterStatus.GREY, Guess.LetterStatus.YELLOW, Guess.LetterStatus.GREY, Guess.LetterStatus.GREY};
        assertArrayEquals(expected, g1.getLetterStatuses());
    }

    /**
     * @author Annika Olson
     * Tests that the proper word length was returned
     */
    @Test 
    public void testGetLengthOfWord() {
        String word = "hello";
        Guess g1 = new Guess(word);
        assertEquals(5, g1.getLengthOfGuessedWord());
    }

    /**
     * @author Annika Olson
     * Tests that modifying a letter status at an index out of bounds throws an exception
     */
    @Test 
    public void testSetLetterStatusAtIndexOutOfBoundsThrowsException() {
        String word = "hello";
        Guess g1 = new Guess(word);
        assertThrows(IndexOutOfBoundsException.class, () -> g1.setLetterStatus(-1, Guess.LetterStatus.YELLOW));
        assertThrows(IndexOutOfBoundsException.class, () -> g1.setLetterStatus(5, Guess.LetterStatus.YELLOW));
    }

    /**
     * @author Annika Olson
     * Test that setting a letter status correctly modifies the array
     */
    @Test 
    public void testSetLetterStatusSetsLetterStatus() {
        String word = "hello";
        Guess g1 = new Guess(word);
        g1.setLetterStatus(0, Guess.LetterStatus.YELLOW);
        g1.setLetterStatus(4, Guess.LetterStatus.GREEN);
        assertEquals(Guess.LetterStatus.YELLOW, g1.getSingleLetterStatus(0));
        assertEquals(Guess.LetterStatus.GREEN, g1.getSingleLetterStatus(4));
    }

}