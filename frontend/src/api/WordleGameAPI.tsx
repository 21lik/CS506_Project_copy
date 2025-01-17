/**
 * This file contains the REST API methods called by the frontend to the
 * backend for the Wordle game page.
 */

const baseUrl = "http://localhost:5000";

/**
 * Sends a REST API POST request to the backend with the user's guess word and
 * returns a string promise, asynchronously containing a string of the letter
 * statuses, corresponding to whether each letter is correct, incorrect, in the
 * wrong position, or part of an invalid guess.
 *
 * @param guessedWord the word the user guessed
 * @returns a string promise
 */
export function postWordleGuess(guessedWord: string) {
  console.log("Posting wordle guess %s", guessedWord); // TODO: test/debug
  const url = baseUrl + "/api/guess-word";

  const response: Promise<Response> = fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      guessedWord: guessedWord,
    }),
  });

  return response;
  // TODO: we will eventually also want to send the guess number (which guess for this user) and username (or user ID).
}

/**
 * Sends a REST API GET request to the backend for the current day's guess word
 * and returns a string promise, asynchronously containing a string of the word
 * of the day.
 *
 * @returns a string promise
 */
export function getWordleActualWord() {
  console.log("Getting wordle actual word"); // TODO: test/debug

  // TODO: implement
}
// TODO: update word in backend (GET, no arguments, just for when playing as guest since login/signup already handled in backend), add streak on win (PUT, probably), reset streak on loss (also PUT, probably), update last online date to current date (no replays on same day)
