package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionProvider {
    private static final String DB_NAME = "attendanceJframeDB";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Mishkat0325@#";

    public static Connection getcon() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(DB_URL + "?useSSL=false", DB_USERNAME, DB_PASSWORD);

            if (!databaseExists(con, DB_NAME)) {
                createDatabase(con, DB_NAME);
            } else {
                System.out.println("Database '" + DB_NAME + "' already exists.");
            }

            return DriverManager.getConnection(DB_URL + DB_NAME + "?useSSL=false", DB_USERNAME, DB_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean databaseExists(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        boolean exists = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'").next();
        stmt.close(); 
        return exists;
    }

    private static void createDatabase(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + dbName);
        System.out.println("DATABASE '" + dbName + "' Created Successfully!"); 
        stmt.close();//miskatul Masabi
        
    }
}
