package es.ulpgc.beachplanner.weather.infrastructure;

import es.ulpgc.beachplanner.weather.model.Beach;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

public class WeatherMapper {

    public WeatherRecord toWeatherRecord(
            Beach beach,
            String time,
            double temperature,
            double windSpeed,
            String capturedAt
    ) {
        return new WeatherRecord(
                beach.name(),
                time,
                temperature,
                windSpeed,
                capturedAt
        );
    }
}