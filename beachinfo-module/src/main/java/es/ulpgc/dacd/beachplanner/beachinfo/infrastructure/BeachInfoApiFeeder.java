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

    @Override
    public List<BeachInfoRecord> fetch() throws Exception {

        String json;

        try {
            json = apiClient.fetchBeachInfoJson();
        } catch (Exception e) {
            System.out.println("Error fetching AEMET beach data: " + e.getMessage());
            return List.of();
        }

        Event event = eventFactory.create(json);

        Gson gson = new Gson();

        String eventJson = gson.toJson(event);

        publisher.publish("BeachInfo", eventJson);

        return mapper.map(json);
    }
}