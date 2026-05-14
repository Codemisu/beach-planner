package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.util.Properties;

public class BeachInfoApiClient {

    private String getApiKey() throws Exception {
        Properties properties = new Properties();

        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties");

        if (input == null) {
            throw new RuntimeException("config.properties not found");
        }

        properties.load(input);

        return properties.getProperty("api.key");
    }

    private String fetchResponse(String endpoint) throws Exception {
        HttpURLConnection connection =
                (HttpURLConnection) new URL(endpoint).openConnection();

        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder response = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

    private String extractDataUrl(String json) {
        String marker = "\"datos\" : \"";
        int start = json.indexOf(marker);

        if (start == -1) {
            marker = "\"datos\":\"";
            start = json.indexOf(marker);
        }

        if (start == -1) {
            throw new RuntimeException(
                    "No se encontró el campo 'datos' en la respuesta: " + json
            );
        }

        start += marker.length();

        int end = json.indexOf("\"", start);

        if (end == -1) {
            throw new RuntimeException(
                    "No se pudo extraer la URL de 'datos'"
            );
        }

        return json.substring(start, end);
    }

    public String fetchBeachInfoJson() throws Exception {
        String apiKey = getApiKey();

        String endpoint =
                "https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/3501601/?api_key="
                        + apiKey;

        String firstJson = fetchResponse(endpoint);

        String dataUrl = extractDataUrl(firstJson);

        return fetchResponse(dataUrl);
    }
}