package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;
import es.ulpgc.dacd.beachplanner.beachinfo.publisher.BeachInfoEventFactory;
import es.ulpgc.dacd.beachplanner.beachinfo.publisher.BeachInfoEventPublisher;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import java.util.List;
import com.google.gson.Gson;

public class BeachInfoApiFeeder implements BeachInfoFeeder {

    private final BeachInfoApiClient apiClient;
    private final BeachInfoMapper mapper;
    private final BeachInfoEventFactory eventFactory;

    private final BeachInfoEventPublisher publisher;

    public BeachInfoApiFeeder(BeachInfoApiClient apiClient,
                              BeachInfoMapper mapper,
                              BeachInfoEventFactory eventFactory,
                              BeachInfoEventPublisher publisher) {
        this.apiClient = apiClient;
        this.mapper = mapper;
        this.eventFactory = eventFactory;
        this.publisher = publisher;
    }

    private String serialize(Event event) {
        Gson gson = new Gson();

        return gson.toJson(event);
    }

    @Override
    public List<BeachInfoRecord> fetch() throws Exception {

        List<BeachInfoRecord> records = new java.util.ArrayList<>();

        String[] beachIds = {
                "3501601", // Las Canteras
                "3501902", // Playa del Inglés
                "3502601"  // Melenara
        };

        for (String beachId : beachIds) {

            try {

                String json = apiClient.fetchBeachInfoJson(beachId);

                System.out.println("Beach ID: " + beachId);
                System.out.println(json.substring(0, Math.min(json.length(), 300)));

                Event event = eventFactory.create(json);

                String eventJson = serialize(event);

                publisher.publish("BeachInfo", eventJson);

                records.addAll(mapper.map(json));
                Thread.sleep(4000);

            } catch (Exception e) {

                System.out.println(
                        "Error fetching AEMET beach data for "
                                + beachId
                                + ": "
                                + e.getMessage()
                );
            }
        }

        return records;
    }
}