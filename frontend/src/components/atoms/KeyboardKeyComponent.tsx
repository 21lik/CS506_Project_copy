import React, { useState } from "react";

interface KeyboardKeyProps {
  letter: string;
  isUsed: boolean;
  onClick: (letter: string) => void;
  className?: string; // Optional for additional styling like "enter" or "delete" keys
}

/**
 * Creates a KeyboardKey component that represents an individual key on the 
 * on-screen keyboard. The key's appearance dynamically updates based on whether 
 * it has been used in guesses. Special keys like Enter and Delete have distinct 
 * styles.
 *
 * @param props - The properties required for the KeyboardKey:
 *   - `label`: The label to display on the key.
 *   - `onClick`: A function to handle the event when the key is clicked.
 *   - `isUsed`: A boolean indicating whether the key has been used in guesses.
 *   - `isSpecial`: A boolean indicating whether the key is a special key 
 *     (e.g., Enter or Delete) to apply specific styles.
 * @returns The rendered KeyboardKey component.
 */
export default function KeyboardKeyComponent({
  letter,
  isUsed,
  onClick,
  className = "",
}: KeyboardKeyProps) {
  const handleClick = () => {
    onClick(letter);
  };

  return (
    <button
      className={`key ${isUsed ? "used-key" : ""} ${className}`}
      onClick={handleClick}
    >
      {letter}
    </button>
  );
}
