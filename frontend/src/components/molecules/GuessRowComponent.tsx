import React from "react";
import GuessBoxComponent from "../atoms/GuessBoxComponent";

interface GuessRowProps {
  guess: string;
  currentGuess: string;
  letterStatuses: string[];
  maxLetters: number;
}

/**
 * Creates a GuessRow component that represents a single row of the gameboard.
 * Each row contains a series of GuessBox components that display the letters 
 * of a player's guess and their statuses (correct, wrong location, or incorrect). 
 * If the row corresponds to the current guess, it updates dynamically as the 
 * player types.
 *
 * @param props - The properties required for the GuessRow:
 *   - `guess`: The string representing the player's guess for this row.
 *   - `currentGuess`: The current guess being typed by the player (applies 
 *     only to the active row).
 *   - `letterStatuses`: An array of statuses for the letters in this row 
 *     (e.g., "G" for correct, "Y" for wrong location, "X" for incorrect).
 *   - `maxLetters`: The maximum number of letters in each row.
 * @returns The rendered GuessRow component.
 */
export default function GuessRowComponent({
  guess,
  currentGuess,
  letterStatuses,
  maxLetters,
}: GuessRowProps) {
  return (
    <div className="guess-row">
      {Array(maxLetters)
        .fill("")
        .map((_, index) => (
          <GuessBoxComponent
            key={index}
            letter={
              index < currentGuess.length
                ? currentGuess[index]
                : guess[index] || ""
            }
            status={guess ? letterStatuses[index] : ""}
          />
        ))}
    </div>
  );
}
