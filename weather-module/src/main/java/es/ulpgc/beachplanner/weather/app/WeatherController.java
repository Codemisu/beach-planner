package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

import java.util.List;

public class WeatherController {
    private final WeatherFeeder feeder;
    private final WeatherRepository repository;
    private final WeatherEventPublisher publisher;

    public WeatherController(
            WeatherFeeder feeder,
            WeatherRepository repository,
            WeatherEventPublisher publisher
    ) {
        this.feeder = feeder;
        this.repository = repository;
        this.publisher = publisher;
    }

    public void run() {
        try {
            List<WeatherRecord> records = feeder.fetch();

            repository.saveAll(records);

            publish(records);

        } finally {
            publisher.close();
        }
    }

    private void publish(List<WeatherRecord> records) {
        for (WeatherRecord record : records) {
            publisher.publish(record);
        }
    }
}