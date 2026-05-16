package es.ulpgc.beachplanner.weather.app;

import es.ulpgc.beachplanner.weather.model.Beach;

import java.util.List;

public class BeachProvider {

    public List<Beach> getBeaches() {
        return List.of(
                new Beach("Las Canteras", 28.1413, -15.4366),
                new Beach("Melenara", 27.9940, -15.3742),
                new Beach("Playa del Inglés", 27.7567, -15.5787)
        );
    }
}