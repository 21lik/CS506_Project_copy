/**
 * This File is used to set up a daily refresh of the wordle
 */
package com.wordle.backend.model; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.ZoneId;

public class Word {

    Connection globalConnection; //used for database connection

    /**
     * Gets database connection
     */
    public Word(){
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
     * Called whenever a player enters a game/logs in, updates daily wordle if it needs to be updated
     */
    public void updateWord(){
        //check if wordle should be updated
        boolean shouldUpdate = checkWordleUpdateStatus();
            if(shouldUpdate){ //get random word and update database
                assignNewWord();
            }
    }

    /**
     * Checks to see if the worlde needs to be updated today or not
     * @return true if it needs to update the wordle
     */
    private boolean checkWordleUpdateStatus(){
        
        LocalDate date = null;
        LocalDate today = LocalDate.now(ZoneId.of("America/Chicago"));
        try{
            String sql = "SELECT last_changed FROM Word";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the date value from the result set
                Date sqlDate = resultSet.getDate("last_changed");

                //convert java.sql.Date to LocalDate
                date = sqlDate.toLocalDate();

                if(today.isAfter(date)){ //if a day has passed since word update
                    return true;
                }
                return false;
            } 
            else{
                System.out.println("No record found");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets a random word and assigns it as the new wordle
     */
    private void assignNewWord(){
        String wordle = "";
        String prevWordle = "";

        //get prev wordle
        prevWordle = getPrevWordle();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Dictionary/5WordDict.txt");
            if (inputStream == null) {
                System.out.println("File not found in classpath: dictionary/5WordDict.txt");
                return;
            }

            // Read lines from the InputStream
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            
            if (!lines.isEmpty()) {
    
                // Generate a random index
                Random random = new Random();
                int randomIndex = random.nextInt(lines.size());
    
                //assign daily word
                wordle = lines.get(randomIndex);
                if(wordle.equals(prevWordle)) { //If wordle is a repeat try again
                    System.out.println("repeat");
                    assignNewWord();
                }
                else { //wordle is not a repeat, update it
                    System.out.println("new word: " + wordle);
                    updateWordle(wordle);
                }
    
            } 
            else {
                System.out.println("The file is empty.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    /**
     * Gets the current wordle
     * @return the current wordle
     */
    private String getPrevWordle(){
        try{
            String sql = "SELECT word FROM Word";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the value from the result set
                return resultSet.getString("word");     
            } 
            else{
                System.out.println("No record found");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * updates the wordle and the last updated date
     * @param wordle new word to set the daily wordle to
     */
    private void updateWordle(String wordle){
        //update wordle
        try{
            String sql = "UPDATE Word SET word = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setString(1, wordle);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        //update date
        LocalDate today = LocalDate.now(ZoneId.of("America/Chicago"));
        Date sqlDate = Date.valueOf(today);
        try{
            String sql = "UPDATE Word SET last_changed = ?";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets todays wordle
     * @return todays daily wordle as a String
     */
    public String getWordle(){
        try{
            String sql = "SELECT word FROM Word";
            PreparedStatement preparedStatement = globalConnection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            //check if a result is returned
            if (resultSet.next()){
                //retrieve the value from the result set
                return resultSet.getString("word");     
            } 
            else{
                System.out.println("No record found");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}