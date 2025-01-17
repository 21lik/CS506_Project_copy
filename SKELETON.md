# Walking Skeleton for Wordle Game by Team ARRAY (T_06)

## Introduction
Our team, **ARRAY (T_06)**, is developing a web-based Wordle game that replicates the core mechanics of the popular word-guessing game while integrating features such as **user login/logout**, **high score tracking**, and **gameplay state management**. This document outlines how different frameworks, tools, and components are integrated to build the application's core functionality, focusing on the **login process** as an example of how the system components work together.

---

## Technology Stack
The following frameworks and tools are used to develop and integrate the different components of the application:

### **Frontend**
- **React**: A JavaScript library used for building user interfaces with a component-based structure.
- **TypeScript**: Provides static typing to enhance code quality and reduce runtime errors.
- **Key Components**:
  - `LoginComponent.tsx`: Handles user login forms and interactions.
  - `LeaderboardComponent.tsx`: Displays user rankings based on high scores.
  - `GameBoardComponent.tsx`: Manages the dynamic display of the Wordle game board.
  - `KeyboardRowComponent.tsx` and `GuessRowComponent.tsx`: Represent individual rows of guesses and keyboard functionality.

### **Backend**
- **Java**: The primary programming language for backend development.
- **Spring Boot**: A framework that simplifies backend development by providing robust features such as dependency injection, REST API creation, and database interaction.
- **Spring Components**:
  - `@Autowired`: Used for dependency injection in classes like `Highscore.java` and `PreGameCheck.java`.
  - REST endpoints: Enable communication between the frontend and backend.

### **Database**
- **MySQL**: A relational database management system used for storing player information, gameplay data, and leaderboard statistics.
  - Tables such as `Player` and `Leaderboard` are central to managing user data and scores.
- **JDBC (Java Database Connectivity)**: Used in backend classes like `Login.java` and `NewUser.java` to connect and interact with the database.

---

## Key Features and Component Integration

### **Login Functionality**
The login process demonstrates seamless integration between the frontend, backend, and database:

#### Flow of Login:
1. **Frontend**:
   - The `LoginComponent.tsx` collects the username and password entered by the user.
   - When the user submits the form, the data is sent as a request to the backend endpoint managed by Spring Boot.

2. **Backend**:
   - The `Login.java` class receives the login request, connects to the MySQL database via JDBC, and validates the user credentials.
   - The query to fetch user credentials:
     ```sql
     SELECT player_id, password FROM Player WHERE username = ?
     ```
   - After validation, the `player_id` is stored in memory for session handling.

3. **Database**:
   - The `Player` table stores user credentials, hashed passwords, and other relevant data such as `is_guest` and `last_online`.

This integration ensures a secure, efficient, and scalable login process.

---

### **Gameplay State Management**
The gameplay experience is dynamically managed by integrating frontend components with backend logic:

- **`GameBoard.java`**:
  - Maintains an array of the user’s guesses (`current_game_guesses`) and updates it as new guesses are made.
  - Key methods:
    - `guessUpdateGameBoard(String new_guess)`: Adds a valid guess to the board.
    - `getCurrentGameGuesses()`: Retrieves the current state of the board.

- **`Guess.java`**:
  - Evaluates guesses and assigns statuses to each letter:
    - **Green**: Correct letter in the correct position.
    - **Yellow**: Correct letter in the wrong position.
    - **Grey**: Incorrect letter.
  - Converts statuses into a format interpretable by the frontend for visual feedback.

---

### **High Score and Streak Tracking**
The system tracks and updates user high scores and streaks, ensuring accurate gameplay statistics.

#### High Score Management:
- **Backend**:
  - `Highscore.java` retrieves and updates high scores in the `Leaderboard` table using queries like:
    ```sql
    SELECT max_streak FROM Leaderboard WHERE player_id = ?
    UPDATE Leaderboard SET max_streak = ? WHERE player_id = ?
    ```

- **Frontend**:
  - `LeaderboardComponent.tsx` displays the updated rankings dynamically.

#### Streak Management:
- **Backend**:
  - `Streak.java` resets or increments streaks based on gameplay outcomes:
    - `resetStreakOnLoss()`: Sets the streak to zero.
    - `addToStreakOnWin()`: Increments the current streak by one.
  - The streak is synchronized with the database via the `Leaderboard` table.

---

## Application Architecture

### **Technology Flow**
The following diagram illustrates the integration between the frontend, backend, and database:

```mermaid
flowchart LR
subgraph Frontend
    A[React with TypeScript]
end

subgraph Backend
    B[Spring Boot]
end

subgraph Database
    C[(MySQL)]
end

A -->|REST API| B
B --> C