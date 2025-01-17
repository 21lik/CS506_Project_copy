import React, { useEffect, useState } from "react";
import "../../App.css";
import HeaderComponent from "../atoms/HeaderComponent";
import GameOverPopUp from "../molecules/GameOverPopUpComponent";
import GameBoardComponent from "../molecules/GameBoardComponent";
import KeyboardComponent from "../molecules/KeyboardComponent";
import { postWordleGuess } from "../../api/WordleGameAPI";

// TODO: determine if we want full or small header component
/**
 * Creates a component for the game page, containing a full header component, a
 * game board, and an on-screen keyboard. The keys of the keyboard can be
 * clicked, or keys of a peripheral keyboard can be pressed, providing input to
 * the game board. Letters contribute to the current guess, backspace deletes
 * the last letter, and enter enters the current guess, making a REST API
 * request to the backend to determine correctness of the word and each letter.
 * Invalid input characters are ignored.
 *
 * @returns the game page component
 */
export default function GamePageComponent() {
  const maxGuesses = 6;
  const maxLetters = 5;
  const [guesses, setGuesses] = useState(Array(maxGuesses).fill(""));
  const [currentGuess, setCurrentGuess] = useState("");
  const [letterStatuses, setLetterStatuses] = useState(
    Array(maxGuesses).fill(Array(maxLetters).fill(""))
  ); // for entered guesses, color of each letter
  const [currentLetterStatuses, setCurrentLetterStatuses] = useState(
    Array(maxLetters).fill("")
  );
  const [usedLetters, setUsedLetters] = useState<string[]>([]);
  const [gameOver, setGameOver] = useState(false);
  const [guessCount, setGuessCount] = useState(0);
  const [keyboardDisabled, setKeyboardDisabled] = useState(false); // prevent guessing when guesses run out or in the process of processing a guess
  const [isWin, setIsWin] = useState(false);
  const winningWord = "ZEBRA"; //temporarily used for winning pop up window

  // QWERTY on-screen keyboard layout
  const row1 = "QWERTYUIOP".split("");
  const row2 = "ASDFGHJKL".split("");
  const row3 = "ZXCVBNM".split("");

  const getUsedBoxClassName = (letterStatus: string): string => {
    // TODO: revise switch values depending on what backend returns
    switch (letterStatus) {
      case "G":
        return "correct-box";
      case "Y":
        return "wrong-location-box";
      case "X":
        return "wrong-letter-box";
    }
    return "used-box"; // TODO: placeholder value, will likely remove in the future
  };

  // Define functions for clicking/pressing keys, checking if valid, etc.
  const handleKeyClick = (letter: string) => {
    if (currentGuess.length < maxLetters) {
      setCurrentGuess((prev) => prev + letter);
    }
  };

  const handleEnterClick = () => {
    if (currentGuess.length === maxLetters) {
      const updatedGuesses = [...guesses];
      const firstEmptyIndex = updatedGuesses.findIndex((guess) => guess === "");

      setKeyboardDisabled(true);

      if (firstEmptyIndex !== -1) {
        const guessResponse: Promise<Response> = postWordleGuess(currentGuess); // TODO: use/test/debug
        console.log(guessResponse);

        // TODO: revise the async callback functions below, add documentation/comments
        guessResponse
          .then((response: Response) => {
            console.log("Response:", response);

            // Update the letter colors
            response.json().then((value: { guessLetters: string }) => {
              const responseLetterStatuses = value.guessLetters;

              updatedGuesses[firstEmptyIndex] = currentGuess;
              setGuesses(updatedGuesses);
              const newLetterStatuses: string[] =
                responseLetterStatuses.split("");
              setCurrentLetterStatuses(newLetterStatuses);

              const updatedLetterStatuses = [...letterStatuses];
              updatedLetterStatuses[firstEmptyIndex] = newLetterStatuses;

              console.log(updatedLetterStatuses);

              setLetterStatuses(updatedLetterStatuses);
            });
          })
          .catch((reason) => {
            console.log("Error:", reason);

            updatedGuesses[firstEmptyIndex] = currentGuess;
            setGuesses(updatedGuesses);
          })
          .finally(() => {
            // Update the keys used
            const newUsedLetters = Array.from(
              new Set([...usedLetters, ...currentGuess.split("")])
            );
            setUsedLetters(newUsedLetters);

            setGuessCount((prev) => prev + 1);

            if (currentGuess === winningWord) {
              setIsWin(true);
              setGameOver(true);
              setCurrentGuess(""); // necessary to prevent same word from showing up again below the winning row
            } else if (guessCount + 1 === maxGuesses) {
              setIsWin(false); // if all guesses used, lose game
              setGameOver(true);
            } else {
              setCurrentGuess("");
              setKeyboardDisabled(false);
            }
          });
      }
    }
  };

  const handleDeleteClick = () => {
    setCurrentGuess((prev) => prev.slice(0, -1));
  };

  const handleKeyPress = (event: KeyboardEvent) => {
    if (keyboardDisabled) return;

    const key = event.key.toUpperCase();

    if (key === "ENTER") {
      handleEnterClick();
    } else if (key === "BACKSPACE") {
      handleDeleteClick();
    } else if (/^[a-zA-Z]$/.test(key)) {
      handleKeyClick(key);
    }
  };

  useEffect(() => {
    window.addEventListener("keydown", handleKeyPress);

    return () => {
      window.removeEventListener("keydown", handleKeyPress);
    };
  }, [currentGuess, guesses]);

  // Create game page
  return (
    <div id="gamepage" className="page">
      <HeaderComponent fullSize={true} />
      <GameBoardComponent
        guesses={guesses}
        currentGuess={currentGuess}
        letterStatuses={letterStatuses}
        maxLetters={maxLetters}
      />
      <KeyboardComponent
        row1={row1}
        row2={row2}
        row3={row3}
        usedLetters={usedLetters}
        onClick={handleKeyClick}
      />
      {gameOver && (
        <GameOverPopUp
          guesses={guessCount}
          onClose={() => setGameOver(false)}
          isWin={isWin}
          correctWord={winningWord}
        />
      )}
    </div>
  );
}
