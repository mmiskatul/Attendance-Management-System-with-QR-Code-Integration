/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author user
 */
public class ConnectionProvider {
    private static final String DB_NAME="attendanceJframeDB";
    private static final String DB_URL="jdbc:Mysql://localhost:3306";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="123456";
    
    public static Connection getcon(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con =DriverManager.getConnection(DB_URL+"?useSSl=false",DB_USERNAME,DB_PASSWORD);
            if(!databaseExits(con,DB_NAME)){
                createDatabase(con,DB_NAME);
            }
            con=DriverManager.getConnection(DB_URL+DB_NAME +"?useSSL=false",DB_USERNAME,DB_PASSWORD);
            return con;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    private static boolean databaseExits(Connection con,String dbName)throws Exception {
        Statement stmnt=con.createStatement();
        return stmnt.executeQuery("SHOW DATABASES LIKE '"+dbName+"'").next();
    }
    private static void createDatabase(Connection con,String dbName)throws Exception{
        Statement stmt =con.createStatement();
        stmt.executeUpdate("CREATE DATABASE "+dbName);
        System.out.print("DATBASE '"+dbName+"' Create Successfully ");
    }
}
