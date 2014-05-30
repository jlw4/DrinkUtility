package database;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Utility for dropping and creating tables
 * 
 * @author John L. Wilson
 *
 */

@SuppressWarnings("unused")
public class CreateTables {
    
    public static void main(String[] args) {
        
        Connection conn = DBConnection.getConnection();
        
        System.out.println("Creating tables...");
        try {
            // get connection
            Statement stmt = conn.createStatement();
            dropDrink(stmt);
            createDrink(stmt);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createAllTables(Statement stmt) {
        createDrink(stmt);
        createCategory(stmt);
        createIngredient(stmt);
        createFavorite(stmt);
        createRating(stmt);
    }
    
    private static void createRating(Statement stmt) {
        try {
            String rating = "CREATE TABLE RATING " +
                    "(drinkid INTEGER REFERENCES DRINK, " +
                    "count INTEGER, sum INTEGER)";
            stmt.executeUpdate(rating);
            System.out.println("created rating");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createFavorite(Statement stmt) {
        try {
            String favorite = "CREATE TABLE FAVORITE " +
                    "(drinkid INTEGER REFERENCES DRINK, " +
                    "userid varchar(63))";
            stmt.executeUpdate(favorite);
            System.out.println("created favorite");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createIngredient(Statement stmt) {
        try {
            String ingredient = "CREATE TABLE INGREDIENT " +
                    "(drinkid INTEGER REFERENCES DRINK, " +
                    "ingredient varchar(63)," + 
                    "ingredientWithPortions varchar(127)," + 
                    "PRIMARY KEY (drinkid, ingredient))";
            stmt.executeUpdate(ingredient);
            System.out.println("created ingredient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createCategory (Statement stmt) {
        try {
            String category = "CREATE TABLE CATEGORY " +
                    "(drinkid INTEGER REFERENCES DRINK, " +
                    "category varchar(63), PRIMARY KEY (drinkid, category))";
            stmt.executeUpdate(category);
            System.out.println("created category");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createDrink(Statement stmt) {
        try {
            String drink = "CREATE TABLE DRINK " +
                    "(id INTEGER PRIMARY KEY, " +
                    " name varchar(127), " +
                    " glass varchar(63), " +
                    " garnish varchar(63), " +
                    " instructions varchar(511), " +
                    " image varchar(127), " +
                    " sweet INTEGER, " +
                    " citrusy INTEGER, " +
                    " bitter INTEGER, " +
                    " herbal INTEGER, " +
                    " minty INTEGER, " +
                    " fruity INTEGER, " +
                    " sour INTEGER, " +
                    " boosy INTEGER, " +
                    " spicy INTEGER, " +
                    " salty INTEGER, " +
                    " creamy INTEGER)";
            stmt.executeUpdate(drink);
            System.out.println("created drink");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private static void dropAllTables(Statement stmt) {
        dropDrink(stmt);
        dropCategory(stmt);
        dropIngredient(stmt);
        dropFavorite(stmt);
        dropRating(stmt);
    }
    
    private static void dropDrink(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE DRINK");
            System.out.println("dropped drink");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void dropCategory(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE CATEGORY");
            System.out.println("dropped category");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void dropIngredient(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE INGREDIENT");
            System.out.println("dropped ingredient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void dropFavorite(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE FAVORITE");
            System.out.println("dropped favorite");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void dropRating(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE RATING");
            System.out.println("dropped rating");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
