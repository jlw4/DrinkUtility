import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Used for visual inspecting of response strings from DrinKDbServer
 * 
 * @author John L Wilson
 */

public class TryDrinkDbServer {

    public static void main(String[] arrrrrgs) {
        try {
            
            // test addrating
            String url = "http://localhost:8000/addrating";
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
            url = "http://localhost:8000/addfavorite";
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
            url = "http://localhost:8000/getfavorites";
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
            url = "http://localhost:8000/removefavorite";
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
            url = "http://localhost:8000/getfavorites";
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
            url = "http://localhost:8000/getuserrating";
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
            url = "http://localhost:8000/getavgrating";
            
            response = new URL(url).openStream();
            System.out.print("Average user ratings : ");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
