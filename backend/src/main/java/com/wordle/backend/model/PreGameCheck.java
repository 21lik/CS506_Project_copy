/**
 * This file is used to check if a player is played today or not
 */
package com.wordle.backend.model; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import com.wordle.backend.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;

public class PreGameCheck {
    
    Connection globalConnection; //used for database connection
    int player_id; //player id for database look-ups

    /**
     * Used to get player_id
     * @param player_id player_id
     */
    public PreGameCheck(int player_id){
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
     * checks if the player can the game or not (guest can always play)
     * run before letting the player run the game
     * @return true if they can play and false of they can't play
     */
    public boolean startPreCheck(){

        if(!checkPlayerStatus()){ //Player is guest, let them play
            return true; 
        }
        else{ //else check if they played today already
            if(alreadyPlayed()){ 
                return false; //already played today, can't play
            }
            else{
                return true; //didn't play yet, they can play
            }
        }
        
    }

    /**
     * checks if the player is logged in or a guest
     * @return true if they are logged in and false if a guest
     */
    private boolean checkPlayerStatus(){
        boolean isLoggedIn = false;

        //gets the is_guest value form the database
        try{
            String sql = "SELECT is_guest FROM Player WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, player_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the is_guest value
                isLoggedIn = !(resultSet.getBoolean("is_guest"));
            } 
            else{
                System.out.println("No record found");
                return false;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }

        return isLoggedIn;
    }

    /**
     * checks to see if the player has already played today
     * @return true if they already played, false if not
     */
    private boolean alreadyPlayed(){
        LocalDate lastPLayedDate = readLastDatePlayed();
        if(lastPLayedDate == null){
            return false;
        }
        LocalDate currentDate = LocalDate.now(ZoneId.of("America/Chicago"));
        //check to see if the last played date is today
        if(lastPLayedDate.getDayOfYear() == currentDate.getDayOfYear() && lastPLayedDate.getYear() == currentDate.getYear()){
            return true; //already played today
        }
        return false; //didn't play today
    }

    /**
     * reads the last played date from the database
     * @return date last played as a LocalDate
     */
    private LocalDate readLastDatePlayed(){
        LocalDate date = null;
        
        //read last date played from databse
        try{
            String sql = "SELECT last_online FROM Player WHERE player_id = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setInt(1, player_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the date value from the result set
                Date sqlDate = resultSet.getDate("last_online");

                //convert java.sql.Date to LocalDate
                if(sqlDate == null){
                    return null;
                }
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


}
