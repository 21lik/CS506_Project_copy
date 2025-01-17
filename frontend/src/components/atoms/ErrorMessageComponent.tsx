import React from "react";

export type ErrorMessageComponentProps = {
  message: string; // error message to display
};

/**
 * Creates a component for an error message, which will be displayed after any
 * failed attempt to log in or create a new user. This message will be
 * displayed below the PLAY button in the respective pages, and the text will
 * be dark red over a light coral background.
 *
 * @param props the error message component props, containing a string field
 * denoting the message to be displayed.
 * @returns an error message component
 */
export default function ErrorMessageComponent(
  props: ErrorMessageComponentProps
) {
  return (
    <div className="error-message">
      <b>Error:</b> {props.message}
    </div>
  );
}
