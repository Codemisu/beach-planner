package es.ulpgc.dacd.beachplanner.businessunit.model;

import es.ulpgc.dacd.beachplanner.businessunit.service.EventStoreReader;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        EventStoreReader reader = new EventStoreReader("eventstore");

        List<String> historicalEvents = reader.readHistoricalEvents();

        for (String event : historicalEvents) {
            System.out.println(event);
        }
    }
}