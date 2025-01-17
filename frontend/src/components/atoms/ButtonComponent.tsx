import React from "react";

export type ButtonComponentProps = {
  text: string; // string on the button
  action: () => void; // a function to execute when the button is pressed
  disabled?: boolean; // true if button is disabled, currently not implemented // TODO: implement
  small?: boolean; // true if small, hyperlink-like version, false if normal version
};

/**
 * Creates a component for a button, which can be used to execute any function
 * (without parameters or return values). There are two versions: the
 * normal-sized button, which has white text on a green background, and the
 * small button, which has underlined dark gray text on a transparent
 * background, similar to a hyperlink.
 *
 * @param props the button component props, containing a string field denoting
 * the text on the button, a function field denoting the action to be executed
 * when the button is clicked, a boolean field denoting whether or not the
 * button is disabled, and a boolean field denoting if the button is the small
 * version.
 * @returns a button component
 */
export default function ButtonComponent(props: ButtonComponentProps) {
  // TODO: implement
  return (
    <button
      className={props.small ? "small-btn" : "btn"}
      onClick={props.action}
    >
      {props.text}
    </button>
  );
}
