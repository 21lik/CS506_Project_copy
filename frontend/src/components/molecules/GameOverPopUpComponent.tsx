import React from 'react';
import ButtonComponent from '../atoms/ButtonComponent';
import { useNavigate } from 'react-router-dom';
import '../../App.css';

interface GameOverPopUpProps {
  guesses: number;
  onClose: () => void;
  isWin: boolean;
  correctWord: string;
}

/**
 * Creates the GameOverPopUp component that displays the game-over screen 
 * when the game finishes. It shows a congratulations message if the user wins 
 * or a sorry message if they lose. The pop-up includes buttons to close it, view leaderboard,
 * or return to home.
 *
 * @param props - The properties required for the GameOverPopUp:
 *   - `guesses`: The total number of guesses the player made.
 *   - `onClose`: A function to handle the event when the pop-up is closed.
 *   - `isWin`: A boolean indicating whether the game was won.
 *   - `correctWord`: The correct word the player was attempting to guess.
 * @returns The rendered GameOverPopUp component.
 */
const GameOverPopUp: React.FC<GameOverPopUpProps> = ({ guesses, onClose, isWin, correctWord }) => {

    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate('/');
    }; 
    const navigateToLeaderboard = () => {
      navigate('/leaderboard');
  }; 

    return (
      <div className="popup-overlay">
        <div className="popup-content">
          <h2>{isWin ? "Congratulations!" : "Game Over"}</h2>
          <p>
            {isWin
              ? `You guessed the word in ${guesses} guesses.`
              : `Sorry, the correct word was ${correctWord}. Try again tomorrow!`}
          </p>
          <div className="buttons">
            <ButtonComponent text="View Leaderboard" action={navigateToLeaderboard} /> 
            <ButtonComponent text="Home" action={navigateToHome} />
            <ButtonComponent text="Close" action={onClose} />
          </div>
          
        </div>
      </div>
    );
  };

export default GameOverPopUp;
