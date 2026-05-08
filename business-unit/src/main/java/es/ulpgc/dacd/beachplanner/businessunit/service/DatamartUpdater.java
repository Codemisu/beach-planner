package es.ulpgc.dacd.beachplanner.businessunit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.ulpgc.dacd.beachplanner.businessunit.model.BeachState;
import es.ulpgc.dacd.beachplanner.common.model.Event;

public class DatamartUpdater {

    private final Gson gson = new Gson();

    public void update(Event event, Datamart datamart) {

        if (!event.getSs().equals("weather-module")) {
            return;
        }

        JsonObject payload = gson.toJsonTree(event.getPayload()).getAsJsonObject();

        String beach = payload.get("beach").getAsString();
        double wind = payload.get("windSpeed").getAsDouble();
        double temperature = payload.get("temperature").getAsDouble();

        BeachState state = datamart.get(beach);

        if (state == null) {
            state = new BeachState(
                    beach,
                    temperature,
                    wind,
                    0,
                    0
            );

            datamart.update(state);
        } else {
            state.setTemperature(temperature);
            state.setWind(wind);
        }

        System.out.println("Updated datamart: " + beach);
    }
}