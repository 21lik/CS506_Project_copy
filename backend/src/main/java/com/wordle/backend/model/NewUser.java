/**
 * This file allows for new account creation
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

public class NewUser {

    Connection globalConnection; //used for database connection
    final int MAX_CHAR_LENGTH = 32;

    /**
     * initializes database connection
     */
    public NewUser(){
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
     * call this to create a new user
     * @return true if user creation successful, false if not
     */
    public String createNewUser(String user, String pass, String pass_val, String email){
        if(!checkIsValid(pass)){ //if pass invalid
            System.out.println("pass not valid");
            return "passbad";
        }
        if(!checkIsValid(user)){ //if user is invalid
            System.out.println("user not valid");
            return "userbad";
        }
        if(!checkIsLongOrEmpty(pass)){ //if pass is too long, fail
            System.out.println("password too long");
            return "passlong";
        }
        if(!checkIsLongOrEmpty(user)){ //if user is too long, fail
            System.out.println("user too long");
            return "userlong";
        }
        if(checkUserValid(user)){ //if user is already in use, fail
            System.out.println("user already in use");
            return "userdup";
        }
        if(!pass.equals(pass_val)){ //if password and validate password don't match return false
            System.out.println("passwords don't match");
            return "passmatch";
        }
        if(!updateUser(user,pass,email)){ //update the player table with new user
            System.out.println("Something went wrong");
            return "error";
        }
        return "success";
    }

    /**
     * checks if given string is valid
     * @param x string to be checked
     * @return true if valid, false if not
     */
    private boolean checkIsValid(String x){
        //check if there are unwanted characters
        String pattern = "^[a-zA-Z0-9 .!?-_]*$";
        if(!x.matches(pattern)){
            return false;
        }

        return true;
    }

    private boolean checkIsLongOrEmpty(String x){
        //check length/is not null
        if(x==null || x.length() > MAX_CHAR_LENGTH){
            return false;
        }
        return true;
    }

    /**
     * checks if user is already in use
     * @param user user
     * @return true is invalid, false if not
     */
    private boolean checkUserValid(String user){
        //check if user is already in use
        try{
            String sql = "SELECT COUNT(*) FROM Player WHERE username = ?";
            PreparedStatement statement = globalConnection.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }   
        }
        catch(SQLException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    /**
     * gets the next available player_id from database
     * @return next player_id as int
     */
    private int getNextPlayerID(){
        try{
            String sql = "SELECT MAX(player_id) AS max_id FROM Player";
            PreparedStatement statement = globalConnection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Integer maxId = resultSet.getInt("max_id");
                    // Check if maxId is null
                    return (maxId != null) ? maxId + 1 : 0; // Start from 0 if no entries exist
                }
                return 0;   
        }
        catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * updates the player table with a new user
     * @param user username
     * @param pass password
     * @param email email
     * @return true if successful and false if not
     */
    private boolean updateUser(String user, String pass, String email){
        //get player_id
        int player_id = getNextPlayerID();
        if(player_id == -1){
            System.out.println("user insertion failed");
            return false;
        }

        try{
            //update database with a new user
            String sql = "INSERT INTO Player (player_id, username, password, email, registration_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = globalConnection.prepareStatement(sql);
                insertStatement.setInt(1, player_id);
                insertStatement.setString(2, user);
                insertStatement.setString(3, pass);
                insertStatement.setString(4, email);

                LocalDate localDate = LocalDate.now(ZoneId.of("America/Chicago"));
                Date sqlDate = Date.valueOf(localDate);
                insertStatement.setDate(5, sqlDate);
                insertStatement.executeUpdate();
                
            // Insert into Leaderboard table with default streak value
            String leaderboardSql = "INSERT INTO Leaderboard (player_id, current_streak, max_streak) VALUES (?, 0, 0)";
            PreparedStatement insertLeaderboardStmt = globalConnection.prepareStatement(leaderboardSql);
                insertLeaderboardStmt.setInt(1, player_id);
                insertLeaderboardStmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
