package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateFavorites {
    
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement stmt = conn.createStatement();

            printAllFavorites(stmt);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void printAllFavorites(Statement stmt) {
        try {
            String favSQL = "SELECT * FROM FAVORITE";
            ResultSet favRS = stmt.executeQuery(favSQL);
            while (favRS.next()) {
                int drinkid = favRS.getInt(1);
                String userid = favRS.getString(2);
                System.out.println("drinkid : " + drinkid + " userid : " + userid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
