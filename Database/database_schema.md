## Player Table

**Primary Key:** `player_id`

The `player_id` serves as the unique identifier for each player, differentiating them from others in the system. The table stores the following information:

- **username**: Player’s username.
- **email**: Player’s email address.
- **password**: Player’s password for authentication.
- **registration_date**: The date when the player registered.
- **last_online**: The date when the player was last marked as online (`is_online = TRUE`).
- **is_online**: A field indicating whether the player is currently online (1 = online, 0 = offline).
- **is_guest**: Indicates if the player is a guest (1 = guest, 0 = registered player).

**Purpose in the game:** This table stores player-specific information, tracks whether the player is online (`is_online`), and records the last date they were online (`last_online`).

---

## Game Table

**Primary Key:** `game_id`

The `game_id` uniquely identifies each game session. The table contains the following information:

- **player_id**: A foreign key linking the game to a specific player.
- **game_date**: The date when the game was played.
- **total_guesses**: The total number of guesses made by the player in the game.

**Foreign Key:**  
- `player_id` → `Player(player_id)` (ON DELETE SET NULL): If a player is deleted, the `player_id` in this table will be set to `NULL`.

**Purpose in the game:** The Game table logs each game session a player starts, recording the date of the game and the total number of guesses made by the player.

---

## Guess Table

**Primary Key:** `guess_id`

The `guess_id` uniquely identifies each guess within a game. The table stores the following information:

- **game_id**: A foreign key linking the guess to a specific game.
- **guess_number**: The order number of the guess (e.g., first guess, second guess, etc.).
- **guess_word**: The word that the player guessed.
- **guess_color**: The color indicating whether the guess is correct (e.g., green for correct, yellow for close, red for incorrect).

**Foreign Key:**  
- `game_id` → `Game(game_id)` (ON DELETE CASCADE): If a game is deleted, all associated guesses are deleted to maintain data integrity.
- `guess_word` → `Word(word)` (ON DELETE RESTRICT): If a word is deleted, the deletion is restricted if any guesses still reference the word.

**Purpose in the game:** The Guess table tracks each individual word guess a player makes during a game. The `game_id` foreign key ensures that guesses are linked to the correct game session.

---

## Word Table

**Primary Key:** `word`

The `word` is the target word for each game session. It stores the following information:

- **word**: The actual word used in the game.
- **last_changed**: The date when the word was last changed or updated.

**Purpose in the game:** The Word table stores the words that players need to guess during each game session. The word’s `last_changed` field helps keep track of when the word was last updated, which may be important for updates or version control.

---

## Leaderboard Table

**Primary Key:** `player_id`

The `player_id` in this table serves as the unique identifier for each player’s position on the leaderboard. The table contains the following fields:

- **max_streak**: The maximum streak of consecutive wins the player has.
- **current_streak**: The current active streak the player is on.

**Foreign Key:**  
- `player_id` → `Player(player_id)` (ON DELETE SET NULL): If a player is deleted, their leaderboard entry will be set to `NULL`.

**Purpose in the game:** The Leaderboard table stores the player's high score, rank, streak, and the date the score was achieved. It is critical for ranking players based on performance and tracking their progress over time.

---

## Ranks Table

**Primary Key:** `rank`

The `rank` in this table uniquely identifies each rank for players in the game. It stores the following information:

- **rank**: The rank assigned to the player based on their performance.
- **player_id**: A foreign key linking the rank to a specific player.

**Foreign Key:**  
- `player_id` → `Player(player_id)` (ON DELETE CASCADE): If a player is deleted, their rank entry will also be deleted to maintain data integrity.

**Purpose in the game:** The Ranks table tracks the rank of each player based on their performance and their position relative to other players. It is important for managing player competition.

---

## Key Deletion Rules:

- **ON DELETE SET NULL**: In the `Game` and `Leaderboard` tables, if a player is deleted, the reference to that player is set to `NULL`, ensuring that game and leaderboard data remains intact.
- **ON DELETE CASCADE**: In the `Guess` and `Ranks` tables, if a game or player is deleted, all associated guesses and ranks are automatically deleted to maintain data integrity.
- **ON DELETE RESTRICT**: In the `Guess` table, if a word is deleted, the deletion is restricted if any guesses still reference the word.

---

## Summary of Key Relationships:

- **Player**: The `Player` table stores player-specific information and tracks whether the player is online (`is_online`) and when they were last online (`last_online`).
- **Game**: The `Game` table logs each game session played by a player, linking each game to a player via `player_id`.
- **Guess**: The `Guess` table tracks each word guess made in a game, linking guesses to specific games via `game_id` and tracking which word was guessed from the `Word` table.
- **Word**: The `Word` table stores the actual words that players are required to guess during the game.
- **Leaderboard**: The `Leaderboard` table ranks players based on their performance, tracking streaks and ranks, and linking these to the `Player` table via `player_id`.
- **Ranks**: The `Ranks` table tracks the rank of players based on their game performance and links to the `Player` table via `player_id`.

---