package es.ulpgc.beachplanner.weather.app;


import es.ulpgc.beachplanner.weather.model.Beach;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoResponseParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import es.ulpgc.beachplanner.weather.infrastructure.OpenMeteoUrlBuilder;
import java.util.ArrayList;
import java.util.List;


public class OpenMeteoFeeder implements WeatherFeeder {

    private final OkHttpClient client;
    private final BeachProvider beachProvider;
    private final OpenMeteoUrlBuilder urlBuilder;
    private final OpenMeteoResponseParser responseParser;

    public OpenMeteoFeeder(
            BeachProvider beachProvider,
            OkHttpClient client,
            OpenMeteoUrlBuilder urlBuilder,
            OpenMeteoResponseParser responseParser
    ) {
        this.beachProvider = beachProvider;
        this.client = client;
        this.urlBuilder = urlBuilder;
        this.responseParser = responseParser;
    }

    @Override
    public List<WeatherRecord> fetch() {
        List<WeatherRecord> records = new ArrayList<>();

        for (Beach beach : beachProvider.getBeaches()) {
            records.addAll(fetchBeachWeather(beach));
        }

        return records;
    }

    private List<WeatherRecord> fetchBeachWeather(Beach beach) {
        String url = urlBuilder.buildFor(beach);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new RuntimeException("Empty response from Open-Meteo");
            }

            String json = response.body().string();

            return responseParser.parse(json, beach);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather for " + beach.name(), e);
        }
    }
}