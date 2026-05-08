package es.ulpgc.dacd.beachplanner.businessunit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.ulpgc.dacd.beachplanner.businessunit.model.BeachState;
import es.ulpgc.dacd.beachplanner.common.model.Event;

public class DatamartUpdater {

    private final Gson gson = new Gson();

    public void update(Event event, Datamart datamart) {

        JsonObject payload = gson.fromJson(
                event.getPayload().toString(),
                JsonObject.class
        );        System.out.println(payload);

        if (event.getSs().equals("weather-module")) {

            String beach = payload.get("beach").getAsString();

            double wind = payload.get("windSpeed").getAsDouble();

            BeachState state = datamart.get(beach);

            if (state != null) {
                state.setWind(wind);
            }
        }
    }
}