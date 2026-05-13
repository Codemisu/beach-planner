package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.model.WeatherRecord;

public interface WeatherEventPublisher {
    void publish(WeatherRecord record);
    void close();
}