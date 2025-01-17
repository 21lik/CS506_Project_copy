import React from "react";
import { useNavigate } from "react-router-dom";

export type HeaderComponentProps = {
  fullSize: boolean; // true for full size header, false for small icon on top left corner 
};

/**
 * Creates a component for a header block, which shows the website title and
 * our team name for all pages of our application. There are two versions of
 * the header component: a full-sized one, which can be placed in the
 * upper-center of the page, and a smaller-sized one, which can be placed in
 * the upper-left corner of the page.
 *
 * @param props the header component props, containing a boolean field denoting
 * whether or not the full-size component version is to be used.
 * @returns a header component
 */
export default function HeaderComponent(props: HeaderComponentProps) {
  // TODO: implement/finish

  const navigate = useNavigate(); 
  const handleHeaderClick = () => {
    navigate("/");
  };

  return (
    <div className="header" onClick={handleHeaderClick} style={{ cursor: "pointer" }}>
      <h1>Wordle</h1>
      <h3>By ARRAY</h3>
    </div>
  );
}
