package es.ulpgc.beachplanner.weather.app;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class WeatherEventBuilder {

    public Event build(WeatherRecord record) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("beach", record.beachName());
        payload.put("forecastTime", record.forecastTime());
        payload.put("temperature", record.temperature());
        payload.put("windSpeed", record.windSpeed());
        payload.put("capturedAt", record.capturedAt());

        return new Event(
                Instant.now().toString(),
                "weather-module",
                payload
        );
    }
}