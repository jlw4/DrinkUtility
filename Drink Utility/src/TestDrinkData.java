import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class TestDrinkData {
    

    public static void main(String[] args) {
        fixRecipesLarge();
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
