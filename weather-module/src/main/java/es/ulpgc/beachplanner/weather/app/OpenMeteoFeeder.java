package es.ulpgc.beachplanner.weather.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.beachplanner.weather.model.Beach;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherMapper;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoUrlBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OpenMeteoFeeder implements WeatherFeeder {

    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final WeatherMapper weatherMapper;
    private final BeachProvider beachProvider;
    private final OpenMeteoUrlBuilder urlBuilder;

    public OpenMeteoFeeder(
            BeachProvider beachProvider,
            OkHttpClient client,
            ObjectMapper mapper,
            WeatherMapper weatherMapper,
            OpenMeteoUrlBuilder urlBuilder
    ) {
        this.beachProvider = beachProvider;
        this.client = client;
        this.mapper = mapper;
        this.weatherMapper = weatherMapper;
        this.urlBuilder = urlBuilder;
    }

    @Override
    public List<WeatherRecord> fetch() {
        List<WeatherRecord> records = new ArrayList<>();

        for (Beach beach : beachProvider.getBeaches()) {
            records.addAll(fetchBeachWeather(beach));
        }

        return records;
    }

    private List<WeatherRecord> fetchBeachWeather(Beach beach) {
        List<WeatherRecord> records = new ArrayList<>();

        String url = urlBuilder.buildFor(beach);

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
                records.add(weatherMapper.toWeatherRecord(
                        beach,
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