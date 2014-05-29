package database;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

/**
 * Parses text files and up uploads drink info to database
 * @author avalanche
 *
 */

public class UpdateDrinks {
    
    // Drink schema: (id, name, glass, garnish, description, instructions, source,
    // sweet, citrusy, bitter, herbal, minty, fruity, sour, boosy, spicy, salty, creamy)
    // id is primary key
    
    public static void main(String[] argv) {
        
        
        try {
            Set<String> drinkNames = getDrinkNames();
            // now print any drinks that need info
            findMissingRecipes(drinkNames);
            // now update drinks
            updateDrinks();
            printNumDrinks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void printNumDrinks() throws Exception {
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT COUNT(*) FROM DRINK";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        System.out.println("We have " + rs.getInt(1) + " drinks in our database");
    }
    
    // Prints all drink names missing recipes to console
    private static void findMissingRecipes(Set<String> drinkNames) {
        try {
            Scanner sc = new Scanner(new File("RecipesFixed.txt"));
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if (drinkNames.contains(line))
                    drinkNames.remove(line);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String missing : drinkNames)
            System.out.println(missing);
    }

    // adds drink and drinkinfo to database
    private static void addDrink(Drink d, DrinkInfo di) {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM DRINK WHERE id=" + d.id;
            stmt.executeUpdate(sql);
            sql = "DELETE FROM CATEGORY WHERE drinkid=" + d.id;
            stmt.executeUpdate(sql);
            sql = "DELETE FROM INGREDIENT WHERE drinkid=" + d.id;
            stmt.executeUpdate(sql);
            sql = "INSERT INTO DRINK VALUES (" +
                         d.id + ", '" +
                         d.name + "', '" +
                         d.glass + "', '" +
                         di.garnish + "', '" +
                         di.description + "', '" +
                         di.instructions + "', '" +
                         di.source + "'";
            for (int i = 0; i < d.attributes.length; i++) {
                sql += ", " + d.attributes[i];
            }
            sql += ")";
            stmt.executeUpdate(sql);
            // now add ingredients
            for (String iwp : di.ingredients) {
                String i = stripPortions(iwp);
                sql = "INSERT INTO INGREDIENT VALUES (" +
                             d.id + ", '" +
                             i + "', '" +
                             iwp + "')";
                stmt.executeUpdate(sql);
            }
            // add categories
            for (String cat : d.categories) {
                String csql = "INSERT INTO CATEGORY VALUES (" +
                              d.id + ", '" + cat + "')";
                stmt.executeUpdate(csql);
            }
            System.out.println("added " + d.name);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get drink names from file
    private static Set<String> getDrinkNames() {
        Set<String> results = new HashSet<String>();
        try {
            Scanner sc = new Scanner(new File("drinksrc.tsv"));
            sc.nextLine(); //throw away first line
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] tokens = s.split("\t");
                String complete = tokens[2];
                if (complete.equals("1")) {
                    // valid drink, add to list
                    results.add(tokens[1]);
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    
    // Old code, needs to be reworked due to design change
    
  /**
   * For testing purposes,resets singleton instance from given streams
   * Also enter debug mode, which doesn't write any data to disc
   */
  public static void updateDrinks() throws Exception {
      Scanner sc = new Scanner(new File("drinksrc.tsv"));
      sc.nextLine(); //throw away first line
      while (sc.hasNextLine()){
          String s = sc.nextLine();
          String[] tokens = s.split("\t");
          String complete = tokens[2];
          if (complete.equals("1")) {
              // valid drink, add to list
              int id = Integer.parseInt(tokens[0]);
              String name = tokens[1];
              // add categories
              List<String> cat = new ArrayList<String>();
              for (String c : tokens[3].split(", ")) {
                  cat.add(c);
              }
              String glass = tokens[4];
              // add attributes
              int[] attributes = new int[11];
              for (int i = 0; i < 11; i++) {
                  attributes[i] = Integer.parseInt(tokens[i + 5]);
              }
              Drink d = new Drink(name, id, 3.0, attributes, cat, glass);
              DrinkInfo di = findDrinkInfo(d);
              if (di != null) {
                  System.out.println("adding " + d.name);
                  addDrink(d, di);
              }
          }
      }
      sc.close();
  }
  
  /**
   * Attempts to remove an unnecessary characters from an ingredient String, and adds it
   * to the set of unique ingredients
   */
  private static String stripPortions(String ingredient) {
      // search for uppercase character
      int i = 0;
      while (!Character.isUpperCase(ingredient.charAt(i)))
          i++;
      // remove first part of String, getting rid of quantity
      ingredient = ingredient.substring(i);
      // remove optional if it is there
      if (ingredient.contains(" (Optional)")) {
          ingredient = ingredient.substring(0, ingredient.length() - 10);
      }
      // check for splash of / dash of etc
      if (ingredient.contains(" of "))
          ingredient = ingredient.split(" of ")[1];
      ingredient = ingredient.trim();
      // ingredient is finally ready to add
      return ingredient;
  }
  
  /**
   * Parses the master list of drink info, searching for drink info for a given drink
   */
  private static DrinkInfo findDrinkInfo(Drink d) throws Exception {
      Scanner sc = new Scanner(new File("RecipesFixed.txt"));
      List<String> lines = new ArrayList<String>();
      String line = sc.nextLine();
      String genericDesc = "This is a really nice drink, we promise!";
      String genericCit = "Cloude Strife";
      while (sc.hasNext()) {
          if (line.equals("")) {
              // found end of drink, process lines
              String name = lines.get(0);
              if (name.equals(d.name)) {
                  // found our drink, build DrinkInfo
                  int len = lines.size();
                  String instructions = lines.get(len - 1);
                  String garnish = lines.get(len - 2).substring(9); // removes "Garnish: "
                  List<String> ingr = new ArrayList<String>();
                  // get the drink object for this DrinkInfo
                  for (int i = 1; i < len - 2; i++) {
                      ingr.add(lines.get(i));
                  }
                  
                  DrinkInfo di = new DrinkInfo(ingr, genericDesc, garnish, instructions, genericCit, d.id);
                  sc.close();
                  return di;
              }
              lines.clear();
          } else {
              lines.add(line);
          }
          line = sc.nextLine();
      }
      sc.close();
      return null;
  }

    
}