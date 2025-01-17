import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import TextboxComponent from "../atoms/TextboxComponent";
import ButtonComponent from "../atoms/ButtonComponent";
import ErrorMessageComponent from "../atoms/ErrorMessageComponent";
import { getLoginRequest } from "../../api/LoginAPI";

/**
 * Creates a component for logging in, containing textboxes for the username
 * and password and a button for logging in. The button navigates the user to
 * the game screen if and only if the username and password match an entry in
 * the user database; that is, they must correspond to an account already
 * created. Otherwise, a message is shown to the user indicating that the
 * username and password are incorrect. There should be only one login
 * component in the entire website.
 *
 * @returns the login component
 */
export default function LoginComponent() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const handlePlayAsUser = () => {
    setErrorMessage("");
    if (username.length === 0 || password.length === 0) {
      // Username or password field is empty, login failed.
      console.log("Please enter a nonempty username and password.");
      setErrorMessage("Please enter a nonempty username and password.");
      return;
    }
    // else if (containsInvalidChars(username) || containsInvalidChars(password)) {
    //   // Username or password field contains invalid chars, login failed.
    //   // TODO: create banner with failed login and respective error message
    //   console.log("Username and password cannot contain invalid characters.");
    //   return;
    // } // TODO: may need this in case invalid chars mess up url arguments

    const loginResponse = getLoginRequest(username, password);
    console.log(loginResponse); // TODO: test/debug
    loginResponse
      .then((response: Response) => {
        response.json().then((value: { message: string }) => {
          const message: string = value.message;
          if (message === "loginfail") {
            console.log("Incorrect login username and password.");
            setErrorMessage("Incorrect login username and password.");
          } else if (message === "gameover") {
            // Player already finished today's game, navigate to leaderboard
            navigate("/leaderboard");
          } else if (message === "gamestart") {
            // Player hasn't finished today's game, navigate to game page
            navigate("/play"); // TODO: restore state once implemented
          } else {
            // Response string not implemented
            console.log(
              "%s is not a valid response string, please debug.",
              message
            ); // TODO: placeholder
            setErrorMessage(
              message + " is not a valid response string, please debug."
            ); // TODO: placeholder
          }
        });
      })
      .catch((reason: any) => {
        console.log("Error:", reason);
        setErrorMessage("Error: " + reason);
      });
  };

  return (
    <div id="login">
      <div className="textboxes">
        <TextboxComponent
          name="username"
          backgroundText="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextboxComponent
          name="password"
          backgroundText="Password"
          isPassword={true}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <div className="buttons">
        <ButtonComponent text="PLAY" action={handlePlayAsUser} />
      </div>
      {errorMessage.length > 0 ? (
        <ErrorMessageComponent message={errorMessage} />
      ) : (
        <></>
      )}
    </div>
  );
}
