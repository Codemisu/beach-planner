package es.ulpgc.beachplanner.weather.app;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class WeatherEventBuilder {

    private final Gson gson = new Gson();

    public Event build(WeatherRecord record) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("beach", record.getBeachName());
        payload.put("forecastTime", record.getForecastTime());
        payload.put("temperature", record.getTemperature());
        payload.put("windSpeed", record.getWindSpeed());
        payload.put("capturedAt", record.getCapturedAt());

        return new Event(
                Instant.now().toString(),
                "weather-module",
                gson.toJson(payload)
        );
    }
}