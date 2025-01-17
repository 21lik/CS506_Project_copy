import React from 'react';
import { render, screen } from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import App from './App';
import LoginPageComponent from './components/pages/LoginPageComponent';
import SignUpPageComponent from './components/pages/SignUpPageComponent';
import LeaderboardPageComponent from './components/pages/LeaderboardPageComponent';
import GameOverPopUp from './components/molecules/GameOverPopUpComponent';
import { click } from '@testing-library/user-event/dist/click';
import { useNavigate } from "react-router-dom";
import { Sign } from 'crypto';

// See <https://testing-library.com/docs/queries/about/> for writing React tests

// Home page
describe("Home Page Rendering", () => {
  test("app renders home page by default", () => {
    const {container} = render(<App />);
    const pageElement = container.getElementsByClassName("page")[0];
    expect(pageElement).toHaveAttribute("id", "homepage");
  });

  test("home page renders login button", () => {
    render(<App />);
    const buttonElement = screen.getByRole("button", {name: /^Log In$/});
    expect(buttonElement).toBeInTheDocument();
  });

  test("home page renders sign up button", () => {
    render(<App />);
    const buttonElement = screen.getByRole("button", {name: /^Sign Up$/});
    expect(buttonElement).toBeInTheDocument();
  });

  test("home page renders view leaderboard button", () => {
    render(<App />);
    const buttonElement = screen.getByRole("button", {name: /^View Leaderboard$/});
    expect(buttonElement).toBeInTheDocument();
  });

  test("home page renders play as guest button", () => {
    render(<App />);
    const buttonElement = screen.getByRole("button", {name: /^Play as Guest$/});
    expect(buttonElement).toBeInTheDocument();
  });
});

// Routing
describe("Navigation from Home Page", () => {
  jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
  }));

  test("navigates to login page from home page", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<App />);

    const loginButton = screen.getByRole("button", { name: /^Log In$/ });
    userEvent.click(loginButton);
    expect(navigate).toHaveBeenCalledWith("/login");
  });

  test("navigates to signup page from home page", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<App />);

    const signupButton = screen.getByRole("button", { name: /^Sign Up$/ });
    userEvent.click(signupButton);
    expect(navigate).toHaveBeenCalledWith("/signup");
  });

  test("navigates to game page from home page (guest)", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<App />);

    const playAsGuest = screen.getByRole("button", { name: /^Play as Guest$/ });
    userEvent.click(playAsGuest);
    expect(navigate).toHaveBeenCalledWith("/play");
  });

  test("navigates to leaderboard page from home page", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<App />);

    const viewBoard = screen.getByRole("button", { name: /^View Leaderboard$/ });
    userEvent.click(viewBoard);
    expect(navigate).toHaveBeenCalledWith("/leaderboard");
  });
});

describe("Login Page Rendering", () => {
  test("login page renders play button", () => {
    render(<LoginPageComponent />);
    const buttonElement = screen.getByRole("button", {name: /^PLAY$/});
    expect(buttonElement).toBeInTheDocument();
  });
  test("login page renders username textbox", () => {
    render(<LoginPageComponent />);
    const textbox = screen.getByRole("textbox", {name: /^Username$/});
    expect(textbox).toBeInTheDocument();
  });
  test("login page renders password textbox", () => {
    render(<LoginPageComponent />);
    const textbox = screen.getByRole("textbox", {name: /^Password$/});
    expect(textbox).toBeInTheDocument();
  });
});

describe("Navigation from Sign Up and Login Page", () => {
  jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
  }));
  test("navigates to game page from login", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<LoginPageComponent />);

    const playButton = screen.getByRole("button", { name: /^PLAY$/ });
    userEvent.click(playButton);
    expect(navigate).toHaveBeenCalledWith("/play");
  });

  test("navigates to game page from sign up", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<SignUpPageComponent />);

    const playButton = screen.getByRole("button", { name: /^PLAY$/ });
    userEvent.click(playButton);
    expect(navigate).toHaveBeenCalledWith("/play");
  });
});

describe("Navigation from leaderboard", () => {
  jest.mock("react-router-dom", () => ({
    ...jest.requireActual("react-router-dom"),
    useNavigate: jest.fn(),
  }));
  test("navigates to home page from leaderboard", () => {
    const navigate = jest.fn();
    (useNavigate as jest.Mock).mockImplementation(() => navigate);
    render(<LeaderboardPageComponent />);

    const playButton = screen.getByRole("button", { name: /^Back to Home$/ });
    userEvent.click(playButton);
    expect(navigate).toHaveBeenCalledWith("/");
  });
});