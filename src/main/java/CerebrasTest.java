import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CerebrasTest {

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("CEREBRAS_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("CEREBRAS_API_KEY not set");
            return;
        }

        String requestBody = """
        {
          "model": "llama3.1-8b",
          "messages": [
            { "role": "user", "content": "Create itinerary for Vilnius for 3 days" }
          ],
          "temperature": 0.2
        }
        """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cerebras.ai/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Response:");
        System.out.println(response.body());
    }
}
