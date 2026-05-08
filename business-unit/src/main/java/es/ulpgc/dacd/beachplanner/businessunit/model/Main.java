package es.ulpgc.dacd.beachplanner.businessunit.model;

import es.ulpgc.dacd.beachplanner.businessunit.infrastructure.EventStoreReader;
import es.ulpgc.dacd.beachplanner.businessunit.service.Datamart;
import es.ulpgc.dacd.beachplanner.businessunit.service.DatamartUpdater;
import es.ulpgc.dacd.beachplanner.businessunit.service.RecommendationService;
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

        RecommendationService recommendationService =
                new RecommendationService();

        System.out.println("\n===== BEACH PLANNER RECOMMENDATIONS =====");

        for (BeachState state : datamart.getAll().values()) {

            String recommendation =
                    recommendationService.recommend(state);

            System.out.println("\n-----------------------------------");
            System.out.println("Playa: " + state.getBeach());
            System.out.println(recommendation);
        }
    }
}