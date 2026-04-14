package es.ulpgc.beachplanner.weather.feeder;

import es.ulpgc.beachplanner.weather.domain.WeatherRecord;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoFeeder implements WeatherFeeder {
    @Override
    public List<WeatherRecord> fetch() {
        List<WeatherRecord> records = new ArrayList<>();
        // Esto es solo para probar que el Controller y el Repository funcionan
        records.add(new WeatherRecord("Las Canteras", "00:15", 22.5, 15.0, "2026-04-10 20:00:00"));
        return records;
    }
}