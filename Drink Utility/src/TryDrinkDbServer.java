import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

/**
 * Used for visual inspecting of response strings from DrinKDbServer
 * 
 * @author John L Wilson
 */

public class TryDrinkDbServer {

    public static void main(String[] arrrrrgs) {
        try {
            
            String hostUrl = "http://localhost:8089/DrinkDbServer";
            
            // test addrating
            String url = hostUrl + "/adduserrating";
            String charset = "UTF-8";
            String userid = "9774d56d682e549c";
            String drinkid = "123";
            String rating = "4";

            String query = String.format("%s&%s&%s", 
                 URLEncoder.encode(userid, charset), 
                 URLEncoder.encode(drinkid, charset),
                 URLEncoder.encode(rating, charset));
            
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            System.out.print("Adding rating : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            // test addfavorite
            url = hostUrl + "/addfavorite";
            charset = "UTF-8";
            userid = "9774d56d682e549c";
            drinkid = "123";

            query = String.format("%s&%s", 
                 URLEncoder.encode(userid, charset), 
                 URLEncoder.encode(drinkid, charset));
            
            connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
            System.out.print("Adding favorite : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            // test getfavorites
            url = hostUrl + "/getuserfavorites";
            charset = "UTF-8";
            userid = "9774d56d682e549c";

            query = String.format("%s", URLEncoder.encode(userid, charset));
            
            connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
            System.out.print("Favorites : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
             // test removefavorite
            url = hostUrl + "/removefavorite";
            charset = "UTF-8";
            userid = "9774d56d682e549c";
            drinkid = "123";

            query = String.format("%s&%s", 
                 URLEncoder.encode(userid, charset), 
                 URLEncoder.encode(drinkid, charset));
            
            connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
            System.out.print("Removing favorite : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            // test remove/getfavorites
            url = hostUrl + "/getuserfavorites";
            charset = "UTF-8";
            userid = "9774d56d682e549c";

            query = String.format("%s", URLEncoder.encode(userid, charset));
            
            connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
            System.out.print("Should be no favorites now : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            System.out.println();
            
            // test getuserrating
            url = hostUrl + "/getuserratings";
            charset = "UTF-8";
            userid = "9774d56d682e549c";

            query = String.format("%s", URLEncoder.encode(userid, charset));
            
            connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            response = connection.getInputStream();
            System.out.print("Getting user ratings : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            // test getavgrating
            url = hostUrl + "/getavgratings";
            
            response = new URL(url).openStream();
            System.out.print("Average user ratings : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            // test getalldrinks
            url = hostUrl + "/getalldrinks";
            
            response = new URL(url).openStream();
            System.out.print("All Drinks : ");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
                String data = reader.readLine();
                Gson gson = new Gson();
                HashMap<String, String> datamap = gson.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
                for (String key : datamap.keySet()) {
                    Drink d = gson.fromJson(key, new TypeToken<Drink>() {}.getType());
                    DrinkInfo di = gson.fromJson(datamap.get(key), new TypeToken<DrinkInfo>() {}.getType());
                    System.out.println(d.id + " " + d.name);
                }
                System.out.println(datamap.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.println("done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
