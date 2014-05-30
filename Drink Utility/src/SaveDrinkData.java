import java.io.FileOutputStream;
import java.util.Random;

import com.onedrinkaway.db.DrinkData;

/**
 * Constructs a new DrinkData from database info and saves it to disc.
 * 
 * @author John L Wilson
 *
 */

public class SaveDrinkData {

    public static void main(String[] args) {
        try {
            Random r = new Random();
            String uid = "" + r.nextInt(100000000);
            DrinkData dd = DrinkData.getDrinkDataDB(uid);
            dd.saveDrinkDataDebug(new FileOutputStream("drinkdata.ser"));
            System.out.println("DrinkData saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
