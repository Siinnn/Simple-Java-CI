import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Calculator {
    public static int add(int a, int b) {
        return (a + b);
    }

    public static int reduce(int a, int b) {
        return (a - b);
    }

    public static void main(String[] args) throws IOException {
        // Obtenir le port depuis la variable d'environnement PORT
        String portStr = System.getenv("PORT");
        int port = portStr != null ? Integer.parseInt(portStr) : 8080;

        // Créer le serveur sur l'adresse 0.0.0.0 pour accepter les connexions externes
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        
        // Route pour l'addition
        server.createContext("/add", (exchange -> {
            // Configuration CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "https://siinnn.github.io");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!exchange.getRequestMethod().equals("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            try {
                // Lire le corps de la requête JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestBody = reader.lines().collect(Collectors.joining());
                
                // Extraire les nombres du JSON
                String[] parts = requestBody.replaceAll("[{}\"]", "").split(",");
                int a = Integer.parseInt(parts[0].split(":")[1].trim());
                int b = Integer.parseInt(parts[1].split(":")[1].trim());

                int result = add(a, b);
                String response = String.format("{\"result\": %d}", result);
                
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                String errorResponse = "{\"error\": \"Invalid request format\"}";
                exchange.sendResponseHeaders(400, errorResponse.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(errorResponse.getBytes());
                }
            }
        }));

        // Route pour la soustraction
        server.createContext("/reduce", (exchange -> {
            // Configuration CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "https://siinnn.github.io");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.getResponseHeaders().add("Content-Type", "application/json");

            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!exchange.getRequestMethod().equals("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            try {
                // Lire le corps de la requête JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestBody = reader.lines().collect(Collectors.joining());
                
                // Extraire les nombres du JSON
                String[] parts = requestBody.replaceAll("[{}\"]", "").split(",");
                int a = Integer.parseInt(parts[0].split(":")[1].trim());
                int b = Integer.parseInt(parts[1].split(":")[1].trim());

                int result = reduce(a, b);
                String response = String.format("{\"result\": %d}", result);
                
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                String errorResponse = "{\"error\": \"Invalid request format\"}";
                exchange.sendResponseHeaders(400, errorResponse.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(errorResponse.getBytes());
                }
            }
        }));

        server.setExecutor(null);
        server.start();
        System.out.println("Server running on port: " + port);
    }
} 