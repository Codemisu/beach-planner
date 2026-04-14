package es.ulpgc.beachplanner.weather.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.beachplanner.weather.model.Beach;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoFeeder implements WeatherFeeder {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<WeatherRecord> fetch() {
        List<WeatherRecord> records = new ArrayList<>();

        List<Beach> beaches = List.of(
                new Beach("Las Canteras", 28.1413, -15.4366),
                new Beach("Las Alcaravaneras", 28.1316, -15.4303),
                new Beach("La Laja", 28.0606, -15.4140)
        );

        for (Beach beach : beaches) {
            records.addAll(fetchBeachWeather(beach));
        } ///para cada playa llamas api, recoges daatos, y añades a la lista final

        return records;
    }

    private List<WeatherRecord> fetchBeachWeather(Beach beach) {
        List<WeatherRecord> records = new ArrayList<>();

        String url = "https://api.open-meteo.com/v1/forecast" ///construyes la url
                + "?latitude=" + beach.latitude()
                + "&longitude=" + beach.longitude()
                + "&hourly=temperature_2m,wind_speed_10m"
                + "&forecast_days=1";

        Request request = new Request.Builder() ///petición http
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new RuntimeException("Empty response from Open-Meteo");
            }

            String json = response.body().string();
            JsonNode root = mapper.readTree(json); //convierte texto, json manejable
            JsonNode hourly = root.get("hourly"); ///ir a datos importantes

            JsonNode times = hourly.get("time");
            JsonNode temperatures = hourly.get("temperature_2m");
            JsonNode windSpeeds = hourly.get("wind_speed_10m");

            String capturedAt = LocalDateTime.now().toString(); ///extraempos los arrays

            for (int i = 0; i < times.size(); i++) {
                records.add(new WeatherRecord(
                        beach.name(),
                        times.get(i).asText(),
                        temperatures.get(i).asDouble(),
                        windSpeeds.get(i).asDouble(),
                        capturedAt
                ));
            } ///para cada hora crea un weather record con playa, hora, temp...

        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather for " + beach.name(), e);
        }

        return records;
    }
}