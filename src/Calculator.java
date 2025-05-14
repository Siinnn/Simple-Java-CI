import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Route pour l'addition
        server.createContext("/add", (exchange -> {
            // Autoriser les requêtes CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Récupérer les paramètres
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");
            int a = Integer.parseInt(params[0].split("=")[1]);
            int b = Integer.parseInt(params[1].split("=")[1]);

            String response = String.valueOf(add(a, b));
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }));

        // Route pour la soustraction
        server.createContext("/reduce", (exchange -> {
            // Autoriser les requêtes CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if (exchange.getRequestMethod().equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Récupérer les paramètres
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");
            int a = Integer.parseInt(params[0].split("=")[1]);
            int b = Integer.parseInt(params[1].split("=")[1]);

            String response = String.valueOf(reduce(a, b));
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }));

        server.setExecutor(null);
        server.start();
        System.out.println("Server running on port: " + port);
    }
} 