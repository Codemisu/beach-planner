package es.ulpgc.beachplanner.weather.model;

public record WeatherRecord(
        String beachName,
        String forecastTime,
        Double temperature,
        Double windSpeed,
        String capturedAt
) {
}