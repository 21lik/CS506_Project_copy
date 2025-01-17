/**
* This file is used to update a players highscore value
*/
package com.wordle.backend.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wordle.backend.service.GameService;
import com.wordle.backend.model.Streak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Highscore {

    Connection globalConnection;
    int player_id;
    
    /**
     * Used to get player_id
     * @param gameService gameService currently in use
     */
    public Highscore(int player_id){
        this.player_id = player_id;
        init();
    }

    /**
     * Initializes database connection
     */
    public void init(){
        //database login
        String host = "db"; 
        String port = "3306"; 
        String database = "WordleDB";
        String user = "root";
        String password = "ARRAY";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        //connect to database
        try{
            globalConnection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates highscore if current streak is greater than highschore
     * call after game completes and streak is updated
     */
    public void updateHighscore(){
        int curScore = getHighScore();
        Streak streak = new Streak(player_id);
        int curStreak = streak.getStreak();
        if(curStreak > curScore){
            updateScore(curStreak);
        }
    }

    /**
     * returns the current highscore from the database
     * @return highscore as int
     */
    private int getHighScore(){     
        int score = 0;

        //read highscore from database
        try{
            String sql = "SELECT max_streak FROM Leaderboard WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, player_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the score value from the result set
                score = resultSet.getInt("max_streak");
            } 
            else{
                System.out.println("No record found");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return score;
    }

    /**
     * updates the highscore value in the database
     * @param score value to update highscore to
     */
    private void updateScore(int score){
        //update database highscore value
        try{
            String sql = "UPDATE Leaderboard SET max_streak = ? WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, player_id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
