/**
 * This file contains the REST API methods called by the frontend to the
 * backend for the login page.
 */

const baseUrl = "http://localhost:5000";

/**
 * Sends a REST API GET request to the backend with the user's login username
 * and password and returns a string promise, asynchronously containing a
 * string denoting the status of username and password combination:
 * "loginfail" (the username and password do not match an existing record),
 * "gameover" (the matching user already finished a game today), or "gamestart"
 * (the matching user has not finished today's game yet).
 *
 * @param username the username the user entered
 * @param password the password the user entered
 * @returns a string promise
 */
export function getLoginRequest(username: string, password: string) {
  console.log(
    "Logging in with username %s and password %s",
    username,
    password
  ); // TODO: test/debug
  const url =
    baseUrl + "/api/login?username=" + username + "&password=" + password;

  const response: Promise<Response> = fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response;
} // TODO: backend will also get info on if player already played (go to game page with victory/defeat popup and boxes filled out), restored state (save the letter colors)
