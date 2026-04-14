package es.ulpgc.beachplanner.weather.model;


public class WeatherRecord {
    private String beachName;
    private String forecastTime;
    private Double temperature;
    private Double windSpeed;
    private String capturedAt;

    public WeatherRecord() {
    }

    public WeatherRecord(String beachName, String forecastTime, Double temperature, Double windSpeed, String capturedAt) {
        this.beachName = beachName;
        this.forecastTime = forecastTime;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.capturedAt = capturedAt;
    }

    public String getBeachName() {
        return beachName;
    }

    public void setBeachName(String beachName) {
        this.beachName = beachName;
    }

    public String getForecastTime() {
        return forecastTime;
    }

    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(String capturedAt) {
        this.capturedAt = capturedAt;
    }
}

