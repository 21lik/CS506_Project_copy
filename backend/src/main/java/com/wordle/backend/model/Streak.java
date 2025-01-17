/**
* This file is used to update/read player win streak values
*/
package com.wordle.backend.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import com.wordle.backend.service.GameService;
import com.wordle.backend.model.Highscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Streak { 

    Connection globalConnection; //used for database connection
    int player_id; //player id for database look-ups


    /**
     * Used to get player_id
     * @param gameService player_id
     */

    public Streak(int player_id){
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
     * needs to be called when a new game is started 
     * Checks to see if the player missed enough time to break their streak and updates the streak
     */
    public void streakCheck(){
        //get last date finished
        LocalDate lastPlayed = readLastDatePlayed();
        if(lastPlayed == null){
            System.out.println("first one");
            updateStreak(-1); //reset streak
            return;
        }
        //get todays date
        LocalDate currentDate = LocalDate.now(ZoneId.of("America/Chicago"));

        /*//check if streak ended
        if(lastPlayed.getDayOfYear()+1 < currentDate.getDayOfYear()){ //if missed a normal day
            updateStreak(-1); //reset streak
        }
        else if(lastPlayed.getDayOfYear() > currentDate.getDayOfYear() && currentDate.getDayOfYear()!=1){ //if missed a day on end of year
            updateStreak(-1); //reset streak
        }
        else if(((lastPlayed.getDayOfYear() == 365)||(lastPlayed.getDayOfYear() == 366)) && currentDate.getDayOfYear()==1 && (currentDate.getYear()-lastPlayed.getYear()!=1)){ //if days look close enough but years apart
            updateStreak(-1); //reset streak
        }*/

        long daysBetween = ChronoUnit.DAYS.between(lastPlayed, currentDate);
        System.out.println("Days Between: " + daysBetween);
        if(daysBetween>=2){
            updateStreak(-1); //reset streak
        }

    }

    /**
     * reads the last played date from the database
     * @return date last played as a LocalDate
     */
    private LocalDate readLastDatePlayed(){
        LocalDate date = null;
        
        //read last date played from database
        try{
            String sql = "SELECT last_online FROM Player WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, player_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the date value from the result set
                Date sqlDate = resultSet.getDate("last_online");
                if(sqlDate == null){
                    return null;
                }
                //convert java.sql.Date to LocalDate
                date = sqlDate.toLocalDate();
            } 
            else{
                System.out.println("No record found");
                return null;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * Call this when game is lost
     * resets the streak to 0
     */
    public void resetStreakOnLoss(){
        updateStreak(-1); //reset streak
    }

    /**
     * Call this when game is won
     * adds one to the streak
     */
    public void addToStreakOnWin(){
        updateStreak(1); //add to streak
    }

    /**
     * updates the current streak value in the database
     * @param value int that is set to -1 when streak is to be reset or a num to be added to the streak
     */
    private void updateStreak(int value){
        //get current streak value
        int streak = getStreak();

        //if update val = -1 then reset streak, else increase it
        if(value != -1){
            streak += value;
            System.out.println("adding to streak: " + streak + "where player_id is: " + player_id);
        }
        else{
            System.out.println("reset streak");
            streak = 0;
        }
        
        //update database streak value
        try{
            String sql = "UPDATE Leaderboard SET current_streak = ? WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, streak);
            preparedStatement.setInt(2, player_id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        Highscore hs = new Highscore(player_id);
        hs.updateHighscore();
    }

    /**
     * gets the current streak from the database
     * @return the current streak as an int
     */
    public int getStreak(){
        int currentStreak = 0;

        //get current streak value from database
        try{
            String sql = "SELECT current_streak FROM Leaderboard WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, player_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the date value from the result set
                currentStreak = resultSet.getInt("current_streak");
            } 
            else{
                System.out.println("No record found");
                return 0;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return currentStreak; 
    }


}

