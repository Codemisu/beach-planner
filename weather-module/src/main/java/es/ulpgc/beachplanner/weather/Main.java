package es.ulpgc.beachplanner.weather;

import es.ulpgc.beachplanner.weather.controller.WeatherController;
import es.ulpgc.beachplanner.weather.feeder.OpenMeteoFeeder;
import es.ulpgc.beachplanner.weather.feeder.WeatherFeeder;
import es.ulpgc.beachplanner.weather.repository.SQLiteWeatherRepository;
import es.ulpgc.beachplanner.weather.repository.WeatherRepository;

public class Main {
    public static void main(String[] args) {

        WeatherFeeder feeder = new OpenMeteoFeeder();
        WeatherRepository repository = new SQLiteWeatherRepository();


        WeatherController controller = new WeatherController(feeder, repository);


        controller.run();
    }
}
