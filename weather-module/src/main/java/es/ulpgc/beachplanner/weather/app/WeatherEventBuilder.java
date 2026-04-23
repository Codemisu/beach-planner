package es.ulpgc.beachplanner.weather.app;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class WeatherEventBuilder {

    public Map<String, Object> build(
            String beach,
            double temperature,
            double windSpeed,
            double waveHeight,
            String condition
    ) {
        Map<String, Object> event = new HashMap<>();

        event.put("ts", Instant.now().toString());
        event.put("ss", "weather-module");

        Map<String, Object> payload = new HashMap<>();
        payload.put("beach", beach);
        payload.put("temperature", temperature);
        payload.put("windSpeed", windSpeed);
        payload.put("waveHeight", waveHeight);
        payload.put("condition", condition);

        event.put("payload", payload);

        return event;
    }
}