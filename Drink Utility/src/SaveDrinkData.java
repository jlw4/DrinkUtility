import java.io.FileOutputStream;

import com.onedrinkaway.db.DrinkData;


public class SaveDrinkData {

    public static void main(String[] args) {
        try {
            DrinkData dd = DrinkData.getDrinkDataDB("test");
            dd.saveDrinkDataDebug(new FileOutputStream("drinkdata.ser"));
            System.out.println("DrinkData saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
