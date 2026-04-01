package es.ulpgc.beachplanner.weather.repository;


import es.ulpgc.beachplanner.weather.domain.WeatherRecord;
import java.util.List;

public class SQLiteWeatherRepository implements WeatherRepository {
    @Override
    public void saveAll(List<WeatherRecord> records) {
        System.out.println("Saving " + records.size() + " records");
    }
}