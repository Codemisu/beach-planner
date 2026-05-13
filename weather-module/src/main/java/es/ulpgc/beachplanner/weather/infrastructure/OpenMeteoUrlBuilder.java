package es.ulpgc.beachplanner.weather.infrastructure;

import es.ulpgc.beachplanner.weather.model.Beach;

public class OpenMeteoUrlBuilder {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public String buildFor(Beach beach) {
        return BASE_URL
                + "?latitude=" + beach.latitude()
                + "&longitude=" + beach.longitude()
                + "&hourly=temperature_2m,wind_speed_10m"
                + "&forecast_days=1";
    }
}