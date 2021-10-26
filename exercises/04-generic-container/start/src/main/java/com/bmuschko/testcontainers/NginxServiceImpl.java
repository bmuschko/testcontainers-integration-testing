package com.bmuschko.testcontainers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NginxServiceImpl implements NginxService {
    private final String endpointUrl;
    
    public NginxServiceImpl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @Override
    public void ping() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(endpointUrl)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IllegalStateException("Cannot reach Ngix server: " + new String(response.body().getBytes()));
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Cannot reach Ngix server");
        }
    }
}
