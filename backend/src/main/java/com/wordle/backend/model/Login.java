package com.wordle.backend.model;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    
    //get database connection
    Connection globalConnection;
    int globalPlayerID;
    /**
     * initializes database connection
     */
    public Login(){
        //database login
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


    public boolean tryLogin(String user, String pass){
        
        try{
            String sql = "SELECT player_id, password FROM Player WHERE username = ?";
            PreparedStatement statement = globalConnection.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    if(resultSet.getString("password").equals(pass)){
                        globalPlayerID = resultSet.getInt("player_id");
                        return true;
                    }
                }   
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getPlayerID(){
        return globalPlayerID;
    }

}