package es.ulpgc.dacd.beachplanner.businessunit.service;

import es.ulpgc.dacd.beachplanner.businessunit.model.BeachState;
import java.util.HashMap;
import java.util.Map;

public class Datamart {

    private final Map<String, BeachState> beaches = new HashMap<>();

    public void update(BeachState state) {
        beaches.put(state.getBeach(), state);
    }

    public BeachState get(String beach) {
        return beaches.get(beach);
    }

    public Map<String, BeachState> getAll() {
        return beaches;
    }
}