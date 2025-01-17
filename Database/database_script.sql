CREATE TABLE Player (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registration_date DATE,
    last_online DATE,
    is_online TINYINT(1) DEFAULT 0,
    is_guest TINYINT(1) DEFAULT 0
);

CREATE TABLE Word (
    word VARCHAR(255) PRIMARY KEY,
    last_changed DATE
);

CREATE TABLE Game (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    game_date DATE,
    total_guesses INT DEFAULT 0,
    FOREIGN KEY (player_id) REFERENCES Player(player_id) ON DELETE CASCADE
);

CREATE TABLE Guess (
    guess_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT,
    guess_number INT,
    guess_word VARCHAR(255),
    guess_color VARCHAR(255),
    FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE,
    FOREIGN KEY (guess_word) REFERENCES Word(word) ON DELETE RESTRICT
);

CREATE TABLE Leaderboard (
    player_id INT PRIMARY KEY,
    max_streak INT DEFAULT 0,
    current_streak INT DEFAULT 0,
    FOREIGN KEY (player_id) REFERENCES Player(player_id) ON DELETE CASCADE
);

CREATE TABLE Ranks (
    rank INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT,
    FOREIGN KEY (player_id) REFERENCES Player(player_id) ON DELETE CASCADE
);
