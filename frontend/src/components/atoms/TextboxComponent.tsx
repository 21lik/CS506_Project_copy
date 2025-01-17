import React from "react";

export type TextboxComponentProps = {
  name: string; // name of the textbox
  label?: string; // label before the textbox, optional
  backgroundText?: string; // string when textbox is empty, optional
  value?: string; // value of the textbox for using hooks, optional
  isPassword?: boolean; // whether or not to use password text, optional
  onChange?: React.ChangeEventHandler<HTMLInputElement>; // function for using hooks, optional
};

/**
 * Creates a component for a textbox, a field where the user can enter one line
 * of text. Hooks can optionally be attached to textbox components such that,
 * when the user changes the entry text, the hook variable can be altered or
 * another function could be executed. This can be useful in case one needs the
 * same value or variable for another component or purpose, such as API calls.
 *
 * @param props the textbox component props, containing the name of the textbox
 * and optionally a label before the textbox, a string in the background when
 * the textbox is empty, and a value string and onChange function for using
 * hooks.
 * @returns a textbox component
 */
export default function TextboxComponent(props: TextboxComponentProps) {
  const inputField =
    props.value !== undefined ? (
      <input
        className="txtbox"
        type={props.isPassword ? "password" : "text"} 
        name={props.name}
        placeholder={props.backgroundText}
        value={props.value}
        onChange={props.onChange}
      />
    ) : (
      <input
        className="txtbox"
        type={props.isPassword ? "password" : "text"} 
        name={props.name}
        placeholder={props.backgroundText}
      />
    ); // use hooks iff provided

  return (
    <label>
      {props.label}
      {inputField}
    </label>
  );
}
