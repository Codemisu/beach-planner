package es.ulpgc.beachplanner.weather.infrastructure;

import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import java.util.List;

public interface WeatherRepository {
    void saveAll(List<WeatherRecord> records);
}