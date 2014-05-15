import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;
import com.onedrinkaway.db.DrinkData;
import com.onedrinkaway.db.DrinkDb;


public class TestDrinkData {
    
    private static DrinkData dd;

    public static void main(String[] args) {
        fixRecipesLarge();
    }
    
    private static void printAllDrinkInfo() {
        dd = DrinkData.getDrinkData();
        Set<Drink> drinks = dd.getAllDrinks();
        for (Drink d : drinks) {
            DrinkInfo di = dd.getDrinkInfo(d);
            System.out.println(di.description);
            System.out.println(di.drinkId);
            System.out.println(di.garnish);
            System.out.println(di.instructions);
            System.out.println(di.source);
            System.out.println();
        }
    }
    
    private static void fixRecipesLarge() {
        try {
            String fileName = "RecipesFixed.txt";
            File file = new File(fileName);
            PrintWriter pw = new PrintWriter(file);
            Scanner sc = new Scanner(new File("RecipesLarge.txt"));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("<div")) {
                    int i = line.indexOf("<");
                    line = line.substring(0, i);
                }
                if (line.contains("Comments")) {
                    line = line.split("Comments")[0];
                }
                pw.println(line);
            }
            sc.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
