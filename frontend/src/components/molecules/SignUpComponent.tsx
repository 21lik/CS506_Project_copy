import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import TextboxComponent from "../atoms/TextboxComponent";
import ButtonComponent from "../atoms/ButtonComponent";
import ErrorMessageComponent from "../atoms/ErrorMessageComponent";
import { putNewUserRequest } from "../../api/SignUpAPI";

/**
 * Creates a component for signing up, containing textboxes for the username
 * and password and a button for logging in. The button navigates the user to
 * the game screen if and only if the username and password are accepted for
 * the user database; that is, they must be unique and strong.
 *
 * @returns the signup component
 */
export default function SignUpComponent() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
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

    const signupResponse = putNewUserRequest(
      username,
      password,
      confirmPassword
    );
    signupResponse
      .then((response: Response) => {
        response.json().then((value: { message: string }) => {
          const message: string = value.message;
          if (message === "passbad") {
            console.log(
              "The password cannot contain invalid characters. Please try again."
            );
            setErrorMessage(
              "The password cannot contain invalid characters. Please try again."
            );
          } else if (message === "userbad") {
            console.log(
              "The username cannot contain invalid characters. Please try again."
            );
            setErrorMessage(
              "The username cannot contain invalid characters. Please try again."
            );
          } else if (message === "passlong") {
            console.log(
              "The password cannot be longer than 32 characters. Please try again."
            ); // TODO: verify/fix max char length
            setErrorMessage(
              "The password cannot be longer than 32 characters. Please try again."
            );
          } else if (message === "userlong") {
            console.log(
              "The username cannot be longer than 32 characters. Please try again."
            ); // TODO: verify/fix max char length
            setErrorMessage(
              "The username cannot be longer than 32 characters. Please try again."
            );
          } else if (message === "userdup") {
            console.log(
              "The requested username is already taken. Please try again."
            );
            setErrorMessage(
              "The requested username is already taken. Please try again."
            );
          } else if (message === "passmatch") {
            console.log("Passwords do not match. Please try again.");
            setErrorMessage("Passwords do not match. Please try again.");
          } else if (message === "error") {
            console.log(
              "An error occurred preventing user creation. Please try again."
            );
            setErrorMessage(
              "An error occurred preventing user creation. Please try again."
            );
          } else if (message === "success") {
            // Signup Successful, navigate to game page
            navigate("/play");
          } else {
            // Response string not implemented
            console.log("%s is not a valid response string.", message);
            setErrorMessage(message + " is not a valid response string.");
          }
        });
      })
      .catch((reason: any) => {
        console.log("Error:", reason);
        setErrorMessage("Error: " + reason);
      });
  };

  return (
    <div id="signup">
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
        <TextboxComponent
          name="confirmpassword"
          backgroundText="Confirm Password"
          isPassword={true}
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
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
