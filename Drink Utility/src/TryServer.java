import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class TryServer {

    public static void main(String[] arrrrrgs) {
        try {
            
            String url = "http://localhost:8000/test";
            String charset = "UTF-8";
            String userid = "9774d56d682e549c";
            String drinkid = "123";
            String rating = "4";
            // ...

            String query = String.format("%s&%s&%s", 
                 URLEncoder.encode(userid, charset), 
                 URLEncoder.encode(drinkid, charset),
                 URLEncoder.encode(rating, charset));
            
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            
            //HttpURLConnection httpConnection = (HttpURLConnection) connection;
            
            //int status = httpConnection.getResponseCode();
            
//            for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
//                System.out.println(header.getKey() + "=" + header.getValue());
//            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }
            
            
            
//            URL url = new URL("http://localhost:8000/test");
          
//            String urlParameters = "param1=a&param2=b&param3=c";
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setInstanceFollowRedirects(false); 
//            connection.setRequestMethod("GET"); 
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//            connection.setRequestProperty("charset", "utf-8");
//            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
//            connection.setUseCaches (false);
//            
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
//            String s;
//            while ((s = br.readLine()) != null) { 
//                System.out.println(s); 
//            } 
//                br.close();
//        
//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
//            wr.writeBytes(urlParameters);
//            wr.flush();
//            wr.close();
//            connection.disconnect();

            } catch (Exception e) {
        }
    } 
}

