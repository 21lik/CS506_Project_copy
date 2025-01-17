/**
 * This file contains the REST API methods called by the frontend to the
 * backend for the home page.
 */

const baseUrl = "http://localhost:5000";

/**
 * Sends a REST API PUT request to the backend with no arguments. This has no
 * purpose except to update the daily word in the backend as necessary.
 *
 * @returns "success" if successful, an error message otherwise
 */
export function putNewGuestGameRequest() {
  console.log("Starting new game as guest"); // TODO: test/debug
  const url = baseUrl + "/api/guest";

  const response: Promise<Response> = fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
  });

  return response;
}
