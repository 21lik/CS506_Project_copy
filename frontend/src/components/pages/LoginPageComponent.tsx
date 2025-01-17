import React from "react";
import "../../App.css";
import LoginComponent from "../molecules/LoginComponent";
import HeaderComponent from "../atoms/HeaderComponent";

/**
 * Creates a component for the login page, containing a full header component
 * and the login component underneath it. Reference the documentation in
 * `components/molecules/LoginComponent.tsx` for login information. There
 * should be exactly one login page component in the entire website.
 *
 * @returns the login page component
 */
export default function LoginPageComponent() {
  return (
    <div id="loginpage" className="page">
      <HeaderComponent fullSize={true} />
      <LoginComponent />
    </div>
  );
}
