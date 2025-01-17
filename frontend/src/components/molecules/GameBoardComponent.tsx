import React from "react";
import GuessRowComponent from "./GuessRowComponent";

interface GameBoardProps {
  guesses: string[];
  currentGuess: string;
  letterStatuses: string[][];
  maxLetters: number;

}
/**
 * Creates the GameBoard component that visually represents the grid of guesses 
 * in the game. Each row corresponds to a player's guess and consists of multiple 
 * GuessBox components representing individual letters. The component dynamically 
 * updates to show the current guess being typed and reflects the correctness of 
 * finalized guesses using color-coded statuses.
 *
 * @param props - The properties required for the GameBoard:
 *   - `guesses`: An array of finalized guesses made by the player.
 *   - `currentGuess`: The current guess being typed by the player.
 *   - `letterStatuses`: A 2D array indicating the correctness of each letter 
 *     in the finalized guesses (e.g., "G" for correct, "Y" for wrong location, 
 *     "X" for incorrect).
 *   - `maxLetters`: The maximum number of letters in each guess.
 * @returns The rendered GameBoard component.
 */
export default function GameBoardComponent({
  guesses,
  currentGuess,
  letterStatuses,
  maxLetters,
}: GameBoardProps) {
  return (
    <div className="gameboard">
      {guesses.map((guess, index) => (
        <GuessRowComponent
          key={index}
          guess={guess}
          currentGuess={index === guesses.findIndex((g) => g === "") ? currentGuess : ""}
          letterStatuses={letterStatuses[index]}
          maxLetters={maxLetters}
        />
      ))}
    </div>
  );
}
