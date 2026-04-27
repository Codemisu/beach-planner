package es.ulpgc.beachplanner.eventstorebuilder.app;

import es.ulpgc.beachplanner.eventstorebuilder.infrastructure.EventStoreWriter;

public class Main {

    public static void main(String[] args) {
        EventStoreWriter writer = new EventStoreWriter();

        EventStoreBuilder builder = new EventStoreBuilder("Weather", writer);

        builder.run();
    }
}