package es.ulpgc.dacd.beachplanner.businessunit.model;

import es.ulpgc.dacd.beachplanner.businessunit.infrastructure.ActiveMQConsumer;
import es.ulpgc.dacd.beachplanner.businessunit.infrastructure.EventStoreReader;
import es.ulpgc.dacd.beachplanner.businessunit.service.Datamart;
import es.ulpgc.dacd.beachplanner.businessunit.service.DatamartUpdater;
import es.ulpgc.dacd.beachplanner.businessunit.service.RecommendationService;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import java.util.Scanner;
import javax.jms.JMSException;
import java.util.List;

public class Main {

    private static final String EVENTSTORE_PATH = "eventstore";
    private static final String BROKER_URL = "tcp://localhost:61616";

    private static final String WEATHER_TOPIC = "Weather";
    private static final String BEACH_INFO_TOPIC = "BeachInfo";

    public static void main(String[] args) throws JMSException {

        Datamart datamart = new Datamart();
        DatamartUpdater updater = new DatamartUpdater();
        RecommendationService recommendationService = new RecommendationService();

        EventStoreReader reader = new EventStoreReader(EVENTSTORE_PATH);
        List<Event> historicalEvents = reader.readHistoricalEvents();

        for (Event event : historicalEvents) {
            updater.update(event, datamart);
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSeleccione una playa:");
        System.out.println("1. Las Canteras");
        System.out.println("2. Las Alcaravaneras");
        System.out.println("3. La Laja");

        String option = scanner.nextLine();

        String selectedBeach;

        switch (option) {
            case "1":
                selectedBeach = "Las Canteras";
                break;
            case "2":
                selectedBeach = "Las Alcaravaneras";
                break;
            case "3":
                selectedBeach = "La Laja";
                break;
            default:
                selectedBeach = "Las Canteras";
        }

        printRecommendation(
                datamart,
                recommendationService,
                selectedBeach
        );

        ActiveMQConsumer weatherConsumer =
                new ActiveMQConsumer(BROKER_URL, WEATHER_TOPIC);

        final int[] weatherEventsReceived = {0};

        weatherConsumer.start(event -> {
            updater.update(event, datamart);
            weatherEventsReceived[0]++;

            if (weatherEventsReceived[0] % 72 == 0) {
                printRecommendation(
                        datamart,
                        recommendationService,
                        selectedBeach
                );
            }
        });

        ActiveMQConsumer beachInfoConsumer =
                new ActiveMQConsumer(BROKER_URL, BEACH_INFO_TOPIC);

        beachInfoConsumer.start(event -> {
            updater.update(event, datamart);

            printRecommendation(
                    datamart,
                    recommendationService,
                    selectedBeach
            );
        });

        System.out.println("Business Unit is running...");
        System.out.println("Listening topics: " + WEATHER_TOPIC + " and " + BEACH_INFO_TOPIC);
    }


    private static void printRecommendation(
            Datamart datamart,
            RecommendationService recommendationService,
            String beach
    ) {

        System.out.println("\n===== BEACH PLANNER RECOMMENDATIONS =====");

        BeachState state = datamart.get(beach);
        if (state == null) {
            System.out.println("No hay datos disponibles para " + beach);
            return;
        }

        String recommendation = recommendationService.recommend(state);

        System.out.println("\n-----------------------------------");
        System.out.println("Playa: " + state.getBeach());
        System.out.println(recommendation);
    }
}
