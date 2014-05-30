package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;



public class DBConnection {
    
    
    public static Connection getConnection() {
        String password = "";
        try {
            Scanner sc = new Scanner(new File("/usr/pwd.txt"));
            password = sc.next();
            sc.close();
        } catch (Exception e) {
            //e.printStackTrace();
            try {
                Scanner sc = new Scanner(new File("C:\\pwd.txt"));
                password = sc.next();
                sc.close();
            } catch (Exception e2) {
                //e2.printStackTrace();
            }
        }
        try {
            String dbName = "onedrinkaway"; 
            String userName = "teamgaia"; 
            String hostname = "onedrinkaway.ctfs3q1wopmj.us-west-2.rds.amazonaws.com";
            String port = "3306";
            
            String jdbcUrl = "jdbc:mysql://" + hostname + ":"
            + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(jdbcUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return null;
        
    }

}
