import com.onedrinkaway.db.DrinkData;
import com.onedrinkaway.db.DrinkDb;


public class TestDrinkData {
    
    private static DrinkData dd;

    public static void main(String[] args) {
        String[] names = DrinkDb.getDrinkNames();
        for (String s : names)
            System.out.println(s);

    }

}
