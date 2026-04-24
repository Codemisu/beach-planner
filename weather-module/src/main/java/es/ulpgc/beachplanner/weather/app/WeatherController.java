package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.infrastructure.WeatherRepository;
import es.ulpgc.beachplanner.weather.infrastructure.WeatherPublisher;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;

import java.util.List;
import java.util.Map;

public class WeatherController {
    private final WeatherFeeder feeder;
    private final WeatherRepository repository;

    private final WeatherEventBuilder eventBuilder = new WeatherEventBuilder();
    private final WeatherPublisher publisher = new WeatherPublisher();

    public WeatherController(WeatherFeeder feeder, WeatherRepository repository) {
        this.feeder = feeder;
        this.repository = repository;
    }

    public void run() {
        List<WeatherRecord> records = feeder.fetch();

        repository.saveAll(records);

        for (WeatherRecord record : records) {
            try {
                Map<String, Object> event = eventBuilder.build(
                        record.getBeachName(),
                        record.getTemperature(),
                        record.getWindSpeed(),
                        0.0,
                        "unknown"
                );

                publisher.publish(event);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}