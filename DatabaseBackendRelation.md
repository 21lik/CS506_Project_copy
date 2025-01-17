# Database Backend Integration Calls

### New User File
- Initializes and stores the following in the `Player` table:
  - **player_id**
  - **username**
  - **email**
  - **password**
  - **registration_date**

### Pregame Check File
- Uses **last_online** from the `Player` table to:
  - Ensure players do not play more than once per day.

### Streak File
- Updates **current_streak** in the `Leaderboard` table based on **last_online** from the `Player` table.
- Compares **current_streak** to **max_streak** and updates **max_streak** if **current_streak** exceeds it.

### Login File
- Retrieves the following from the `Player` table to verify user credentials:
  - **player_id**
  - **username**
  - **password**
- Uses **is_online** and **is_guest** (boolean values) to determine whether the user is logging in or playing as a guest.

### Game File
- Updates the following in the `Game` table at the end of each game:
  - **game_id**
  - **game_date** (can be set using **last_online** from `Player` as it’s updated at game end)
  - **total_guesses**

### Guess File
- Stores the following in the `Guess` table after each guess:
  - **guess_id**
  - **guess_number**
  - **guess_word**
- **guess_number** can be used to calculate **total_guesses** in the `Game` table.

### Highscore File
- Checks **max_streak** from the `Leaderboard` table each time the **streak** is updated in the `Streak File`.
- Updates **max_streak** only if the **current_streak** exceeds it.

### Leaderboard Page (Frontend)
- Displays **ranked** from the `Leaderboard` table using an `ORDER BY` query based on **max_streak** to rank players from highest to lowest.
