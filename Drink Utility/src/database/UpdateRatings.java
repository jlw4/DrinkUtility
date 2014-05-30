package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/**
 * Used for viewing and updating ratings
 * @author Avalanche
 *
 */

public class UpdateRatings {

    public static void main(String[] args) {
        
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement stmt = conn.createStatement();
            //addNRatings(200, stmt, "testuser");
            printAllRatings(stmt);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Adds a rating for every drink id from 1 to n, giving them a 2, 3 or 4
     */
    public static void addNRatings(int n, Statement stmt, String user) {
        try {
            Random r = new Random();
            for (int drinkid = 1; drinkid <= n; drinkid++) {
                String remSQL = "DELETE FROM RATING WHERE drinkid = " + drinkid +
                                " AND userid = '" + user + "'";
                stmt.executeUpdate(remSQL);
                int rating = 2 + r.nextInt(3);
                String addSQL = "INSERT INTO RATING VALUES (" +
                             drinkid + ", " + rating + ", '" + user + "')";
                stmt.executeUpdate(addSQL);
                System.out.println("Added rating : " + rating + " to drinkid : " + drinkid);
            }
            System.out.println("Successfully added " + n + " ratings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void printAvgRatings(Statement stmt) {
        try {
            String ratingSQL = "SELECT drinkid, AVG(rating) FROM RATING GROUP BY drinkid";
            ResultSet ratingRS = stmt.executeQuery(ratingSQL);
            while (ratingRS.next()) {
                int drinkid = ratingRS.getInt(1);
                double rating = ratingRS.getDouble(2);
                System.out.println("drinkid : " + drinkid + " rating : " + rating);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void printAllRatings(Statement stmt) {
        try {
                String ratingSQL = "SELECT * FROM RATING";
                ResultSet ratingRS = stmt.executeQuery(ratingSQL);
                while (ratingRS.next()) {
                    int drinkid = ratingRS.getInt(1);
                    int rating = ratingRS.getInt(2);
                    String userid = ratingRS.getString(3);
                    System.out.println("drinkid : " + drinkid + " rating : " + rating + " userid : " + userid);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
