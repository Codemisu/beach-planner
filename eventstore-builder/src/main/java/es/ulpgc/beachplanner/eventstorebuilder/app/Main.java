package es.ulpgc.beachplanner.eventstorebuilder.app;

import es.ulpgc.beachplanner.eventstorebuilder.infrastructure.EventStoreWriter;

public class Main {

    public static void main(String[] args) throws Exception {

        String eventJson = """
                {
                    "ts":"2026-04-27T12:00:00Z",
                    "ss":"beachinfo-module",
                    "payload":"hola"
                }
                """;

        EventStoreWriter writer = new EventStoreWriter();

        writer.write("BeachInfo", eventJson);

        System.out.println("Test completed");
    }
}