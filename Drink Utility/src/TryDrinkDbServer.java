import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class TryDrinkDbServer {

    public static void main(String[] arrrrrgs) {
        try {
            
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
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }

            } catch (Exception e) {
        }
    } 
}
