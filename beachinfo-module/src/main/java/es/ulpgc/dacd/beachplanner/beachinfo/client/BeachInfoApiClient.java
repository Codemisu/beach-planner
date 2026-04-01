package es.ulpgc.dacd.beachplanner.beachinfo.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BeachInfoApiClient {

    private static final String API_KEY = "API_KEY";

    public String fetchBeachInfoJson() throws Exception {
        String endpoint = "https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/3501601/?api_key=" + API_KEY;

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP error code: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return response.toString();
    }
}