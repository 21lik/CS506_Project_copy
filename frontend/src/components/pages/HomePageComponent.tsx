import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../App.css";
import ButtonComponent from "../atoms/ButtonComponent";
import HeaderComponent from "../atoms/HeaderComponent";
import { putNewGuestGameRequest } from "../../api/HomeAPI";
import ErrorMessageComponent from "../atoms/ErrorMessageComponent";

/**
 * Creates a component for the homepage, containing a full header component and
 * four button components. The buttons can be clicked to navigate to the login
 * screen, the signup screen, the leaderboard screen, and the game screen (if
 * playing as a guest). The latter button is smaller than the other three,
 * appearing as a hyperlinked text with transparent background rather than a
 * button with a green background. There should be exactly one home page
 * component in the entire website.
 *
 * @returns the home page component
 */
export default function HomePageComponent() {
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const handleLogin = () => {
    navigate("/login");
  };

  const handleSignUp = () => {
    navigate("/signup");
  };

  const handleViewLeaderboard = () => {
    navigate("/leaderboard");
  };

  const handlePlayAsGuest = () => {
    setErrorMessage("");
    const guestResponse = putNewGuestGameRequest();
    guestResponse
      .then((response: Response) => {
        response.json().then((value: { success: boolean }) => {
          if (value.success) {
            navigate("/play");
          } else {
            console.log("Daily word update failed, please try again later."); // TODO: placeholder
            setErrorMessage(
              "Daily word update failed, please try again later."
            );
            navigate("/play"); // TODO: temporary hotfix for final demo - currently API for this doesn't work
          }
        });
      })
      .catch((reason: any) => {
        console.log("Error:", reason);
        setErrorMessage("Error: " + reason);
        navigate("/play"); // TODO: temporary hotfix for final demo - currently API for this doesn't work
      });
  };

  return (
    <div id="homepage" className="page">
      <HeaderComponent fullSize={true} />
      <div className="buttons">
        <ButtonComponent text="Log In" action={handleLogin} />
        <ButtonComponent text="Sign Up" action={handleSignUp} />
        <ButtonComponent
          text="View Leaderboard"
          action={handleViewLeaderboard}
        />
        <ButtonComponent
          text="Play as Guest"
          small={true}
          action={handlePlayAsGuest}
        />
      </div>
      {errorMessage.length > 0 ? (
        <ErrorMessageComponent message={errorMessage} />
      ) : (
        <></>
      )}
    </div>
  );
}
