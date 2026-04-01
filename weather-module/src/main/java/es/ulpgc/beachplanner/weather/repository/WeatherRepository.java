package es.ulpgc.beachplanner.weather.repository;

import es.ulpgc.beachplanner.weather.domain.WeatherRecord;
import java.util.List;

public interface WeatherRepository {
    void saveAll(List<WeatherRecord> records);
}