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
    public String fetchBeachInfoJson() throws Exception {
        String apiKey = getApiKey();

        String endpoint = "https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/3501601/?api_key=" + apiKey;
        // PRIMERA LLAMADA
        HttpURLConnection conn1 = (HttpURLConnection) new URL(endpoint).openConnection();
        conn1.setRequestMethod("GET");

        BufferedReader reader1 = new BufferedReader(
                new InputStreamReader(conn1.getInputStream())
        );

        StringBuilder response1 = new StringBuilder();
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            response1.append(line1);
        }
        reader1.close();

        String firstJson = response1.toString();

        // EXTRAER URL DE "datos"
        String marker = "\"datos\" : \"";
        int start = firstJson.indexOf(marker);

        if (start == -1) {
            marker = "\"datos\":\"";
            start = firstJson.indexOf(marker);
        }

        if (start == -1) {
            throw new RuntimeException("No se encontró el campo 'datos' en la respuesta: " + firstJson);
        }

        start += marker.length();
        int end = firstJson.indexOf("\"", start);

        if (end == -1) {
            throw new RuntimeException("No se pudo extraer la URL de 'datos'");
        }

        String dataUrl = firstJson.substring(start, end);

        // SEGUNDA LLAMADA
        HttpURLConnection conn2 = (HttpURLConnection) new URL(dataUrl).openConnection();
        conn2.setRequestMethod("GET");

        BufferedReader reader2 = new BufferedReader(
                new InputStreamReader(conn2.getInputStream())
        );

        StringBuilder response2 = new StringBuilder();
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            response2.append(line2);
        }
        reader2.close();

        return response2.toString();
    }
}