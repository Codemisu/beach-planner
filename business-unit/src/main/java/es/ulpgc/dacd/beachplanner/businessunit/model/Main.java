package es.ulpgc.dacd.beachplanner.businessunit.model;

import es.ulpgc.dacd.beachplanner.businessunit.infrastructure.EventStoreReader;

import java.util.List;
import es.ulpgc.dacd.beachplanner.common.model.Event;
public class Main {

    public static void main(String[] args) {


        EventStoreReader reader = new EventStoreReader("eventstore");

        List<Event> historicalEvents = reader.readHistoricalEvents();

        for (Event event : historicalEvents) {
            System.out.println(event);
        }
    }
}