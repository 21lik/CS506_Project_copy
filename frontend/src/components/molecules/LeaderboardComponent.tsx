import "../../App.css";

type Player = {
    username: string;
    currentStreak: number;
    maxStreak: number;
  };
  
  type LeaderboardProps = {
    players: Player[];
  };
  
  export default function LeaderboardComponent({ players }: LeaderboardProps) {
    const topPlayers = [...players]
      .sort((a, b) => b.maxStreak - a.maxStreak || b.currentStreak - a.currentStreak)
      .slice(0, 5);
  
    return (
      <div className="leaderboard">
        <h2>Leaderboard</h2>
        <table>
          <thead>
            <tr>
              <th>Username</th>
              <th>Current Streak</th>
              <th>Max Streak</th>
            </tr>
          </thead>
          <tbody>
            {topPlayers.map((player, index) => (
              <tr key={index}>
                <td>{player.username}</td>
                <td>{player.currentStreak}</td>
                <td>{player.maxStreak}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }