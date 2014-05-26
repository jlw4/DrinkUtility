
    
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TestServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String requestMethod = t.getRequestMethod();
            String response = requestMethod + " " + tryConnection();
            String query = t.getRequestURI().getQuery();
            System.out.println("Your query is : " + query);
            System.out.println("Query length : " + query.length());
            response += query;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private static String tryConnection() {
        if (getConnection() == null)
            return "Connection failed :(";
        else
            return "Connected Success!";
    }
    
    public static Connection getConnection() {
        try {
            String password = "ghghghgh1";
            String dbName = "onedrinkaway"; 
            String userName = "teamgaia"; 
            String hostname = "onedrinkaway.ctfs3q1wopmj.us-west-2.rds.amazonaws.com";
            String port = "3306";
            
            String jdbcUrl = "jdbc:mysql://" + hostname + ":"
            + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection(jdbcUrl);
            System.out.println("Opened database successfully");
            
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return null;
    }

}
