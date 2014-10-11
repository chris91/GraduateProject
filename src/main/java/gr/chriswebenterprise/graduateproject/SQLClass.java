/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.chriswebenterprise.graduateproject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris91
 */
public class SQLClass {

    private Connection connection;
    private String url;
    private String usr;
    private String pwd;
    private long startTime, endTime, elapsedTime;
    private Statement statement;
    private ResultSet resultSet;

    public SQLClass() {
        try {
            url = "jdbc:mysql://localhost:3306/snapdb";
            usr = "root";
            pwd = "";
            connection = (Connection) DriverManager.getConnection(url, usr, pwd);
            statement = (Statement) connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SQLClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void queryDB(String query) {
        try {
            startTime = System.currentTimeMillis();
            resultSet = statement.executeQuery(query);
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
        } catch (SQLException ex) {
            Logger.getLogger(SQLClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printResults() {
        try {
            while (resultSet.next()) {
                //System.out.print(resultSet.getInt(1));
                //System.out.print(": ");
                System.out.println(resultSet.getInt(1));
            }
            System.out.println("Elapsed Time:   "+elapsedTime+" ms");
        } catch (SQLException ex) {
            Logger.getLogger(SQLClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
