/**
 * This file contains the REST API methods called by the frontend to the
 * backend for the signup page.
 */

const baseUrl = "http://localhost:5000";

/**
 * Sends a REST API PUT request to the backend with the new user's username and
 * password and returns a string promise, asynchronously containing one of the
 * following strings, each representing a different message:
 * * "passbad": password contains invalid characters.
 * * "userbad": username contains invalid characters.
 * * "passlong": password is too long.
 * * "userlong": username is too long.
 * * "userdup": username is already in use.
 * * "passmatch": password and verify password don't match.
 * * "error": some error ocurred that prevented user creation.
 * * "success": user creation succeeded.
 *
 * @param username the username the user entered
 * @param password the password the user entered
 * @returns a string promise
 */
export function putNewUserRequest(
  username: string,
  password: string,
  confirmPassword: string
) {
  console.log(
    "Signing up with username %s and password %s, confirmed %s",
    username,
    password,
    confirmPassword
  ); // TODO: test/debug
  const url = baseUrl + "/api/new-user";

  // TODO: are we doing email as well? if so fix frontend, if not fix backend
  const response: Promise<Response> = fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: username,
      password: password,
      pass_val: confirmPassword,
    }),
  });

  return response;
}
