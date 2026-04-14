package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;

public class WeatherController {
    private final WeatherFeeder feeder;
    private final WeatherRepository repository;

    public WeatherController(WeatherFeeder feeder, WeatherRepository repository) {
        this.feeder = feeder;
        this.repository = repository;
    }

    public void run() {
        repository.saveAll(feeder.fetch());
    }
}