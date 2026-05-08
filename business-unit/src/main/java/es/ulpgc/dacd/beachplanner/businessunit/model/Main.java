package es.ulpgc.dacd.beachplanner.businessunit.model;

import es.ulpgc.dacd.beachplanner.businessunit.infrastructure.EventStoreReader;
import es.ulpgc.dacd.beachplanner.businessunit.service.Datamart;
import es.ulpgc.dacd.beachplanner.businessunit.service.DatamartUpdater;
import es.ulpgc.dacd.beachplanner.common.model.Event;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        EventStoreReader reader = new EventStoreReader("eventstore");

        List<Event> historicalEvents = reader.readHistoricalEvents();

        Datamart datamart = new Datamart();

        DatamartUpdater updater = new DatamartUpdater();

        for (Event event : historicalEvents) {

            updater.update(event, datamart);
        }

        System.out.println(datamart.getAll());
    }
}