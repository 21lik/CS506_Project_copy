import React from "react";
import "../../App.css";
import SignUpComponent from "../molecules/SignUpComponent";
import HeaderComponent from "../atoms/HeaderComponent";

/**
 * Creates a component for the sign up page, containing a full header component
 * and the login component underneath it. Reference the documentation in
 * `components/molecules/SignUpComponent.tsx` for sign up information. 
 *
 * @returns the sign up page component
 */
export default function LoginPageComponent() {
  return (
    <div id="signuppage" className="page">
      <HeaderComponent fullSize={true} />
      <SignUpComponent />
    </div>
  );
}
