import React, { useEffect, useState } from "react";
import "../../App.css";
import { useNavigate } from "react-router-dom";
import HeaderComponent from "../atoms/HeaderComponent";
import LeaderboardComponent from "../molecules/LeaderboardComponent";
import ButtonComponent from "../atoms/ButtonComponent";

/**
 * Creates a component for the leaderboard page which can be navagable
 * from the homepage and gamepage. The leaderboard displays the top 5
 * players and their streaks using the LeaderBoardComponent. 
 */
export default function LeaderboardPageComponent() {
    const navigate = useNavigate();

    const handleNavigateToHome = () => {
        navigate("/");
      };

    //TODO: implement API requests to get top 5 players from database
    const players = [
        { username: "Kevin", currentStreak: 10, maxStreak: 15 },
        { username: "Annika", currentStreak: 5, maxStreak: 20 },
        { username: "Muhammad", currentStreak: 7, maxStreak: 18 },
        { username: "Zach", currentStreak: 8, maxStreak: 12 },
        { username: "Myranda", currentStreak: 3, maxStreak: 10 },
      ];

      return(
        <div id="leaderboard" className="page">
        <HeaderComponent fullSize={true} />
        <LeaderboardComponent players={players} />
        <ButtonComponent
          text="Back to Home"
          small={true}
          action={handleNavigateToHome}
        />
        </div>
      )
}
