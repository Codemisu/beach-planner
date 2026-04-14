package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import java.util.List;

public interface WeatherFeeder {
    List<WeatherRecord> fetch();
}
