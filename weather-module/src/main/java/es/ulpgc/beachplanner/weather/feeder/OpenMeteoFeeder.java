package es.ulpgc.beachplanner.weather.feeder;

import es.ulpgc.beachplanner.weather.domain.WeatherRecord;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoFeeder implements WeatherFeeder {
    @Override
    public List<WeatherRecord> fetch() {
        return new ArrayList<>();
    }
}