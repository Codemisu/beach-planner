package es.ulpgc.beachplanner.weather.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoUrlBuilder;
import es.ulpgc.beachplanner.weather.infrastructure.SQLiteWeatherRepository;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherMapper;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherPublisher;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;
import okhttp3.OkHttpClient;

public class Main {
    public static void main(String[] args) {

        BeachProvider beachProvider = new BeachProvider();

        WeatherFeeder feeder = new OpenMeteoFeeder(
                beachProvider,
                new OkHttpClient(),
                new ObjectMapper(),
                new WeatherMapper(),
                new OpenMeteoUrlBuilder()
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