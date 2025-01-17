import React, { useState } from "react";

interface GuessBoxProps {
  letter: string;
  status: string;
}
/**
 * Creates a GuessBox component that represents an individual letter in a guess. 
 * Each box visually indicates the correctness of the letter using color-coded 
 * statuses (e.g., green for correct, yellow for wrong position, gray for incorrect).
 *
 * @param props - The properties required for the GuessBox:
 *   - `letter`: The letter to be displayed in the box.
 *   - `status`: The status of the letter, determining the styling of the box:
 *     - "G" for correct,
 *     - "Y" for wrong location,
 *     - "X" for incorrect,
 *     - default styling for empty boxes.
 * @returns The rendered GuessBox component.
 */

export default function GuessBoxComponent({ letter, status }: GuessBoxProps) {
  const getClassName = (): string => {
    switch (status) {
      case "G":
        return "correct-box";
      case "Y":
        return "wrong-location-box";
      case "X":
        return "wrong-letter-box";
      default:
        return "";
    }
  }
  return <div className={`guess-box ${getClassName()}`}>{letter}</div>;
}
