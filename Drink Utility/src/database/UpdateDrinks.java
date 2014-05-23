package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
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
    // drinkId is primary key
    
    public static void main(String[] argv) {
        
        Connection conn = DBConnection.getConnection();
        
        try {
            Statement stmt = conn.createStatement();
            // get drink names from database
            Set<String> drinkNames = getDrinkNames(stmt);
            // now parse text files and get DrinkData
            InputStream drinkIs = new FileInputStream(new File("drinks.tsv"));
            InputStream drinkInfoIs = new FileInputStream(new File("RecipesBeta.txt"));
            // TODO, use code below to get a list of Drinks and DrinkInfo found in files
            // TODO, upload drinks that are not found in drinkNames
            
            

//            for (Drink d : drinks) {
//                if (!drinkNames.contains(d.name)) {
//                    // drink is not in database, add it
//                    DrinkInfo di = dd.getDrinkInfo(d);
//                    Set<String> ingr = dd.getIngredients();
//                    addDrink(d, di, ingr, stmt);
//                }
//            }
            
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addDrink(Drink d, DrinkInfo di, Set<String> ingr, Statement stmt) {
        try {
            String sql = "INSERT INTO DRINK VALUES (" +
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
            for (String i : ingr) {
                for (String iwp : di.ingredients) {
                    if (iwp.contains(i)) {
                        // found our pair
                        sql = "INSERT INTO INGREDIENT VALUES (" +
                                     d.id + ", '" +
                                     i + "', '" +
                                     iwp + "')";
                        stmt.executeUpdate(sql);
                    }
                }
            }
            // add categories
            for (String cat : d.categories) {
                String csql = "INSERT INTO CATEGORY VALUES (" +
                              d.id + ", '" + cat + "')";
                stmt.executeUpdate(csql);
            }
            System.out.println("added " + d.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getDrinkNames(Statement stmt) {
        Set<String> results = new HashSet<String>();
        try {
            String sql = "SELECT name FROM DRINK";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                results.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    
    // Old code, needs to be reworked due to design change
    
//  /**
//   * For testing purposes,resets singleton instance from given streams
//   * Also enter debug mode, which doesn't write any data to disc
//   */
//  public static DrinkData getDrinkData(InputStream drinkIs, InputStream drinkInfoIs) {
//      instance = new DrinkData();
//      Scanner sc = new Scanner(drinkIs);
//      sc.nextLine(); //throw away first line
//      while (sc.hasNextLine()){
//          String s = sc.nextLine();
//          String[] tokens = s.split("\t");
//          String complete = tokens[2];
//          if (complete.equals("1")) {
//              // valid drink, add to list
//              int id = Integer.parseInt(tokens[0]);
//              String name = tokens[1];
//              // add categories
//              List<String> cat = new ArrayList<String>();
//              for (String c : tokens[3].split(", ")) {
//                  cat.add(c);
//                  instance.categories.add(c);
//              }
//              String glass = tokens[4];
//              // add attributes
//              int[] attributes = new int[11];
//              for (int i = 0; i < 11; i++) {
//                  attributes[i] = Integer.parseInt(tokens[i + 5]);
//              }
//              double rating = getAvgRating(id);
//              Drink d = new Drink(name, id, rating, attributes, cat, glass);
//              instance.addDrink(d);
//          }
//      }
//      sc.close();
//      findDrinkInfo(drinkInfoIs);
//      return instance;
//  }
//  
//  /**
//   * Parses the master list of drink info, searching for drinks that exist in drinkSet
//   * This does not currently work on an Android device
//   */
//  private static void findDrinkInfo(InputStream is) {
//      Scanner sc = new Scanner(is);
//      List<String> lines = new ArrayList<String>();
//      String line = sc.nextLine();
//      String genericDesc = "This is a really nice drink, we promise!";
//      String genericCit = "Cloude Strife";
//      while (sc.hasNext()) {
//          if (line.equals("")) {
//              // found end of drink, process lines
//              String name = lines.get(0);
//              if (instance.namesToDrinks.containsKey(name)) {
//                  // we have this drink, build a DrinkInfo for it
//                  int len = lines.size();
//                  String instructions = lines.get(len - 1);
//                  String garnish = lines.get(len - 2).substring(9); // removes "Garnish: "
//                  List<String> ingr = new ArrayList<String>();
//                  // get the drink object for this DrinkInfo
//                  Drink d = instance.namesToDrinks.get(name);
//                  for (int i = 1; i < len - 2; i++) {
//                      ingr.add(lines.get(i));
//                      instance.addIngredient(d, lines.get(i));
//                  }
//                  
//                  DrinkInfo di = new DrinkInfo(ingr, genericDesc, garnish, instructions, genericCit, d.id);
//                  instance.info.put(d, di);
//              }
//              lines.clear();
//          } else {
//              lines.add(line);
//          }
//          line = sc.nextLine();
//      }
//      sc.close();
//  }
//  
//  /**
//   * Attempts to remove an unnecessary characters from an ingredient String, and adds it
//   * to the set of unique ingredients
//   */
//  private void addIngredient(Drink d, String ingredient) {
//      // search for uppercase character
//      int i = 0;
//      while (!Character.isUpperCase(ingredient.charAt(i)))
//          i++;
//      // remove first part of String, getting rid of quantity
//      ingredient = ingredient.substring(i);
//      // remove optional if it is there
//      if (ingredient.contains(" (Optional)")) {
//          ingredient = ingredient.substring(0, ingredient.length() - 10);
//      }
//      // check for splash of / dash of etc
//      if (ingredient.contains(" of "))
//          ingredient = ingredient.split(" of ")[1];
//      ingredient = ingredient.trim();
//      // ingredient is finally ready to add
//      instance.addIngredientTrimed(d, ingredient);
//  }
}
