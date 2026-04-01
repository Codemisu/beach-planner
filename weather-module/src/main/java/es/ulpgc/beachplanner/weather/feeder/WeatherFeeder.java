package es.ulpgc.beachplanner.weather.feeder;

import es.ulpgc.beachplanner.weather.domain.WeatherRecord;
import java.util.List;

public interface WeatherFeeder {
    List<WeatherRecord> fetch();
}
