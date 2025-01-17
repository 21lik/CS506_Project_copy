import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import HomePageComponent from './components/pages/HomePageComponent';
import LoginPageComponent from './components/pages/LoginPageComponent';
import GamePageComponent from './components/pages/GamePageComponent';
import SignUpPageComponent from './components/pages/SignUpPageComponent'
import LeaderBoardPageComponent from './components/pages/LeaderboardPageComponent'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePageComponent />} />
        <Route path="/play" element={<GamePageComponent />} />
        <Route path="/login" element={<LoginPageComponent />} />
        <Route path="/signup" element={<SignUpPageComponent />} />
        <Route path="/leaderboard" element={<LeaderBoardPageComponent />} />  
      </Routes>
    </Router>
  );
}

export default App;