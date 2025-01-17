import React, { useState } from "react";
import KeyboardKeyComponent from "../atoms/KeyboardKeyComponent";

interface KeyboardRowProps {
  letters: string[];
  usedLetters: string[];
  onClick: (letter: string) => void;
  isSpecialRow?: boolean;
}

/**
 * Creates a KeyboardRow component representing a single row of keys on the 
 * on-screen keyboard. Each key is rendered as a KeyboardKey component.
 *
 * @param props - The properties required for the KeyboardRow:
 *   - `keys`: An array of letters representing the keys in the row.
 *   - `usedLetters`: An array of letters that have already been used in guesses.
 *   - `onKeyClick`: A function to handle the event when a key in the row is clicked.
 * @returns The rendered KeyboardRow component.
 */
export default function KeyboardRowComponent({
  letters,
  usedLetters,
  onClick,
  isSpecialRow = false,
}: KeyboardRowProps) {
  return (
    <div className="keyboard-row">
      {isSpecialRow && (
        <KeyboardKeyComponent
          letter="DELETE"
          isUsed={false}
          onClick={onClick}
          className="delete-key"
        />
      )}
      {letters.map((letter) => (
        <KeyboardKeyComponent
          key={letter}
          letter={letter}
          isUsed={usedLetters.includes(letter)}
          onClick={onClick}
        />
      ))}
      {isSpecialRow && (
        <KeyboardKeyComponent
          letter="ENTER"
          isUsed={false}
          onClick={onClick}
          className="enter-key"
        />
      )}
    </div>
  );
}
