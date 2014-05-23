package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;



public class DBConnection {
    
    private static Connection c;
    
    public static Connection getConnection() {
        try {
            Scanner sc = new Scanner(new File("pwd.txt"));
            String password = "ghghghgh1";
            
            String dbName = "onedrinkaway"; 
            String userName = "teamgaia"; 
            String hostname = "onedrinkaway.ctfs3q1wopmj.us-west-2.rds.amazonaws.com";
            String port = "3306";
            
            String jdbcUrl = "jdbc:mysql://" + hostname + ":"
            + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(jdbcUrl);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        
        return c;
        
    }

}
