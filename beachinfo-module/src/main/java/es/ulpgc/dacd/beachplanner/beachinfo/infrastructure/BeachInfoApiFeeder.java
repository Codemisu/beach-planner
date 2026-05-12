package es.ulpgc.dacd.beachplanner.beachinfo.infrastructure;

import es.ulpgc.dacd.beachplanner.beachinfo.model.BeachInfoRecord;
import es.ulpgc.dacd.beachplanner.beachinfo.publisher.BeachInfoEventFactory;
import es.ulpgc.dacd.beachplanner.beachinfo.publisher.BeachInfoEventPublisher;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import java.util.List;

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

        BeachInfoEventFactory eventFactory =
                new BeachInfoEventFactory();

        Event event = eventFactory.create(json);

        BeachInfoEventPublisher publisher = new BeachInfoEventPublisher();
        publisher.publish("BeachInfo", event);

        return mapper.map(json);
    }
}