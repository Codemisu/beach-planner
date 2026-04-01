package es.ulpgc.beachplanner.weather;

import es.ulpgc.beachplanner.weather.controller.WeatherController;
import es.ulpgc.beachplanner.weather.feeder.OpenMeteoFeeder;
import es.ulpgc.beachplanner.weather.repository.SQLiteWeatherRepository;

public class Main {
    public static void main(String[] args) {
        WeatherController controller = new WeatherController(
                new OpenMeteoFeeder(),
                new SQLiteWeatherRepository()
        );

        controller.run();
    }
}
