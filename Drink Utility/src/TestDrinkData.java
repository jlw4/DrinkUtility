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
        DrinkDb.saveDrinkData();
    }
    
    private static void printAllDrinkInfo() {
        dd = new DrinkData();
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
    
    private static void fixIngredientsLarge() {
        try {
            String fileName = "IngredientsFixed.txt";
            File file = new File(fileName);
            PrintWriter pw = new PrintWriter(file);
            Scanner sc = new Scanner(new File("IngredientsLarge.txt"));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("<div")) {
                    int i = line.indexOf("<");
                    line = line.substring(0, i);
                }
                pw.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
