package es.ulpgc.beachplanner.weather.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoResponseParser;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoUrlBuilder;
import es.ulpgc.beachplanner.weather.infrastructure.SQLiteWeatherRepository;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherMapper;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherPublisher;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;
import okhttp3.OkHttpClient;

public class Main {
    public static void main(String[] args) {

        BeachProvider beachProvider = new BeachProvider();

        OpenMeteoResponseParser responseParser = new OpenMeteoResponseParser(
                new ObjectMapper(),
                new WeatherMapper()
        );

        WeatherFeeder feeder = new OpenMeteoFeeder(
                beachProvider,
                new OkHttpClient(),
                new OpenMeteoUrlBuilder(),
                responseParser
        );

        WeatherRepository repository = new SQLiteWeatherRepository();

        WeatherEventPublisher publisher = new WeatherPublisher();

        WeatherController controller = new WeatherController(
                feeder,
                repository,
                publisher
        );

        controller.run();
    }
}