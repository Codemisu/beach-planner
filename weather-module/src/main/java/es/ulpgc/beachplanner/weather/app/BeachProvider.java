package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.model.Beach;

import java.util.List;

public class BeachProvider {

    public List<Beach> getBeaches() {
        return List.of(
                new Beach("Las Canteras", 28.1413, -15.4366),
                new Beach("Las Alcaravaneras", 28.1316, -15.4303),
                new Beach("La Laja", 28.0606, -15.4140)
        );
    }
}