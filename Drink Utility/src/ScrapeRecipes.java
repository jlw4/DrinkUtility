import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Utility class for database, contains various functions for managing,
 * populating and storing data.
 * 
 * @author John L Wilson
 *
 */
public class ScrapeRecipes {

    public static void main(String[] args) {
        //List<String> drinks = getDrinkNames();
        scrapeGoodCocktails(1, 2950, 1);
    }
    
    /**
     * Parses Drinks.csv and returns a List of all drink names found
     */
    public static List<String> getDrinkNames() {
        List<String> list = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new File("Drinks.csv"));
            sc.nextLine(); //throw away first line
            String s;
            String name;
            String bad = "--";
            while (sc.hasNextLine()){
                s = sc.nextLine();
                name = s.split(",")[1];
                if (!name.equals(bad)) {
                    list.add(name);
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Scrapes drink names, ingredients, garnish and instructions from goodcocktails.com
     * 
     * @param start the drink number to start with
     * @param end the drink number to end with
     * @param docNum the number of the document to write to
     */
    public static void scrapeGoodCocktails(int start, int end, int docNum) {
        PrintWriter pw = null;
        try {
            String fileName = "Ingredients" + docNum + ".txt";
            File file = new File(fileName);
            pw = new PrintWriter(file);
            while (start < end) {
                // concatenate drink number to make address
                String req = "http://www.goodcocktails.com/recipes/printerfriendly.php?drinkID=" + start;
                URLConnection con = (new URL(req)).openConnection();
                // set user properties so connection looks like a browser
                String usrProp = "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)";
                con.setRequestProperty("User-Agent", usrProp);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = in.readLine();
                boolean done = false; // use to kill rest of loop
                while (line != null && !done) {
                    if (line.contains("<h4>Ingredients</h4>")) {
                        done = true;
                        // found ingredients list
                        String name = line.substring(26).split("' src='")[0];
                        // remove " Drink"
                        name = name.substring(0, name.length() - 6);
                        pw.println(name);
                        in.readLine(); // skips <ul>
                        line = in.readLine();
                        String endIngr = "</ul>";
                        while (line != null && !line.equals(endIngr)) {
                            line = line.replaceAll("<a.*top'>", ""); // remove links
                            line = line.replaceAll("<.{0,3}>", ""); // remove other tags
                            line = line.replaceAll(" +", " "); // replace multiple spaces with single
                            line = line.trim();
                            pw.println(line);
                            line = in.readLine();
                        }
                        // done reading ingredients, now find garnish and instructions
                        line = in.readLine();
                        line = line.replaceAll("<.{0,7}>", "");
                        String[] tokens = line.split("Instructions");
                        pw.println(tokens[0].trim());
                        pw.println(tokens[1].trim());
                        pw.println();
                    }
                    line = in.readLine();
                }
                start++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.close();
    }
    
    /**
     * Spoofs a connection to wiki.webtender.com, gathering 
     * 
     * @param drinks
     */
    public static void scrapeIngredients(List<String> drinks) {
        int count = 0;
        for (String drink : drinks) {
            
            try {
                String search = "drinksmixer " + drink;
                String req = "http://www.google.com/search?q="+
                        URLEncoder.encode(search, "UTF8")+"&"+
                        "btnI="+URLEncoder.encode("I'm Feeling Lucky", "UTF8");

                HttpURLConnection con = (HttpURLConnection) (new URL(req)).openConnection();

                con.setRequestProperty("User-Agent", "IXWT");
                con.setInstanceFollowRedirects(false);
                
                String loc = con.getHeaderField("Location"); 
                
                
                
                
                if (loc!=null) {
                    System.out.print("The prophet spoke thus: " + loc);
                    count++;
                } else {
                    System.out.println("failed: " + req);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        System.out.println(count + " / " + drinks.size());
    }

}