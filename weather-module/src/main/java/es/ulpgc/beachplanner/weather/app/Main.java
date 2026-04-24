package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.infrastructure.SQLiteWeatherRepository;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;

public class Main {
    public static void main(String[] args) {

        WeatherFeeder feeder = new OpenMeteoFeeder();
        WeatherRepository repository = new SQLiteWeatherRepository();

        WeatherController controller = new WeatherController(feeder, repository);

        controller.run();
    }
}
