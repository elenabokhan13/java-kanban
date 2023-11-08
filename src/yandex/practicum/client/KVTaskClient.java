package yandex.practicum.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String tokenApiClient;
    private String urlAddress;
    private HttpClient client = HttpClient.newHttpClient();

    public KVTaskClient(String urlAddress) throws IOException, InterruptedException {
        this.urlAddress = urlAddress;
        URI url = URI.create(urlAddress + "/register");
        HttpRequest requestRegister = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(requestRegister,
                HttpResponse.BodyHandlers.ofString());
        tokenApiClient = response.body();
    }

    public String getTokenApiClient() {
        return tokenApiClient;
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        String urlNew = urlAddress + "/save" + "/%3C" + key + "%3E?API_TOKEN=" + key;
        URI url = URI.create(urlNew);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest
                .BodyPublishers.ofString(json)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String load(String key) throws IOException, InterruptedException {
        URI url = URI.create(urlAddress + "/load" + "/%3C" + key + "%3E?API_TOKEN=" + key);
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
