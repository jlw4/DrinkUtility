import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class DrinkInfoFinder {

    public static void main(String[] args) {
        generateIngredientsList("RecipesBeta.txt");
    }
    
    /**
     * Searches RecipesLarge for recipes, and saves them to fileName
     * @param fileName the file to write to
     */
    private static void generateIngredientsList(String fileName) {
        Set<String> names = getDrinkNames();
        try {
            File file = new File(fileName);
            PrintWriter pw = new PrintWriter(file);
            Scanner sc = new Scanner(new File("RecipesFixed.txt"));
            List<String> lines = new ArrayList<String>();
            String line = sc.nextLine();
            while (sc.hasNext()) {
                if (line.equals("")) {
                    // found end of drink, process lines
                    String name = lines.get(0);
                    if (names.contains(name)) {
                        // we have this drink, write lines to pw
                        for (String s : lines)
                            pw.println(s);
                        pw.println();
                    }
                    lines.clear();
                } else {
                    lines.add(line);
                }
                line = sc.nextLine();
            }
            sc.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static Set<String> getDrinkNames() {
        Set<String> result = new HashSet<String>();
        try {
            Scanner sc = new Scanner(new File("drinks.tsv"));
            sc.nextLine(); //throw away first line
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] tokens = s.split("\t");
                String complete = tokens[2];
                if (complete.equals("1")) {
                    // valid drink, add to list
                    result.add(tokens[1]);
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
