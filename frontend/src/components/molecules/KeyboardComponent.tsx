import React from "react";
import KeyboardRowComponent from "../molecules/KeyboardRowComponent";

interface KeyboardProps {
  row1: string[];
  row2: string[];
  row3: string[];
  usedLetters: string[];
  onClick: (letter: string) => void;
}

/**
 * Creates the Keyboard component that provides an on-screen keyboard for user input.
 * The keyboard contains three rows of keys, along with the Enter and Delete buttons. 
 * Keys dynamically update to show whether they have been used, and their appearance 
 * can reflect gameplay statuses.
 *
 * @param props - The properties required for the Keyboard:
 *   - `onKeyClick`: A function to handle the event when a key is clicked.
 *   - `onEnterClick`: A function to handle the event when the Enter button is clicked.
 *   - `onDeleteClick`: A function to handle the event when the Delete button is clicked.
 *   - `usedLetters`: An array of letters that have already been used in guesses.
 * @returns The rendered Keyboard component.
 */
export default function KeyboardComponent({
  row1,
  row2,
  row3,
  usedLetters,
  onClick,
}: KeyboardProps) {
  return (
    <div className="keyboard">
      <KeyboardRowComponent letters={row1} usedLetters={usedLetters} onClick={onClick} />
      <KeyboardRowComponent letters={row2} usedLetters={usedLetters} onClick={onClick} />
      <KeyboardRowComponent
        letters={row3}
        usedLetters={usedLetters}
        onClick={onClick}
        isSpecialRow
      />
    </div>
  );
}
