import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServer {

    public static void main(String[] args) throws IOException {
        // Starts the web server locally on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Server started at http://localhost:8080/");

        // Route to serve our landing page layout
        server.createContext("/", new UIHandler());

        // Route to catch data when users submit the booking request form
        server.createContext("/submit-appointment", new FormHandler());

        server.setExecutor(null); 
        server.start();
    }

    // Serves the HTML frontend interface file
    static class UIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get("index.html"));
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    // Handles processing data filled in the form inputs
    static class FormHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Read the incoming form values from the input stream
                java.util.Scanner s = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A");
                String formData = s.hasNext() ? s.next() : "";
                
                // Print received details into the system terminal logs
                System.out.println("Received Booking Submission: " + formData);

                // Send a confirmation page layout back to the user
                String responseText = "<html><body><h2>Thank you! Appointment requested successfully.</h2><a href='/'>Go Back</a></body></html>";
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, responseText.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseText.getBytes());
                os.close();
            } else {
                // Reject alternative request methods (GET, PUT, etc.)
                exchange.sendResponseHeaders(405, -1); 
            }
        }
    }
}