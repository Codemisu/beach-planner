package es.ulpgc.beachplanner.weather.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.beachplanner.weather.model.Beach;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoResponseParser {

    private final ObjectMapper mapper;
    private final WeatherMapper weatherMapper;

    public OpenMeteoResponseParser(ObjectMapper mapper, WeatherMapper weatherMapper) {
        this.mapper = mapper;
        this.weatherMapper = weatherMapper;
    }

    public List<WeatherRecord> parse(String json, Beach beach) {
        try {
            List<WeatherRecord> records = new ArrayList<>();

            JsonNode root = mapper.readTree(json);
            JsonNode hourly = root.get("hourly");

            JsonNode times = hourly.get("time");
            JsonNode temperatures = hourly.get("temperature_2m");
            JsonNode windSpeeds = hourly.get("wind_speed_10m");

            String capturedAt = LocalDateTime.now().toString();

            for (int i = 0; i < times.size(); i++) {
                records.add(weatherMapper.toWeatherRecord(
                        beach,
                        times.get(i).asText(),
                        temperatures.get(i).asDouble(),
                        windSpeeds.get(i).asDouble(),
                        capturedAt
                ));
            }

            return records;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing Open-Meteo response", e);
        }
    }
}