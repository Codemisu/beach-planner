package es.ulpgc.dacd.beachplanner.businessunit.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import es.ulpgc.dacd.beachplanner.businessunit.model.BeachState;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
public class DatamartUpdater {

    private final Gson gson = new Gson();

    public void update(Event event, Datamart datamart) {
        if (event.getSs().equals("weather-module")) {

            JsonObject payload =
                    gson.toJsonTree(event.getPayload()).getAsJsonObject();

            String beach =
                    payload.get("beach").getAsString();

            double wind =
                    payload.get("windSpeed").getAsDouble();

            double temperature =
                    payload.get("temperature").getAsDouble();

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

            System.out.println("Datamart updated: " + beach + " (weather)");        }

        if (event.getSs().equals("beachinfo-module")) {

            JsonArray array = JsonParser
                    .parseString(event.getPayload().toString())
                    .getAsJsonArray();

            JsonObject beachInfo = array.get(0).getAsJsonObject();

            String beach = beachInfo.get("nombre").getAsString();

            JsonObject prediction =
                    beachInfo.getAsJsonObject("prediccion")
                            .getAsJsonArray("dia")
                            .get(0)
                            .getAsJsonObject();

            String skyState =
                    prediction.getAsJsonObject("estadoCielo")
                            .get("descripcion1")
                            .getAsString();

            String waveState =
                    prediction.getAsJsonObject("oleaje")
                            .get("descripcion1")
                            .getAsString();

            int uvIndex =
                    prediction.getAsJsonObject("uvMax")
                            .get("valor1")
                            .getAsInt();

            double waterTemperature =
                    prediction.getAsJsonObject("tAgua")
                            .get("valor1")
                            .getAsDouble();

            BeachState state = datamart.get(beach);

            if (state == null) {
                state = new BeachState(beach, 0, 0, 0, 0);
                datamart.update(state);
            }

            state.setSkyState(skyState);
            state.setWaveState(waveState);
            state.setUvIndex(uvIndex);
            state.setWaterTemperature(waterTemperature);

            System.out.println("Datamart updated: " + beach + " (beach info)");
            return;
        }
    }
}