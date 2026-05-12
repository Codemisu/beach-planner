package es.ulpgc.dacd.beachplanner.beachinfo.publisher;
import es.ulpgc.dacd.beachplanner.beachinfo.publisher.BeachInfoEventPublisher;

import es.ulpgc.dacd.beachplanner.common.model.Event;
import java.time.Instant;

public class BeachInfoEventFactory {

    public Event create(String payload) {
        return new Event(
                Instant.now().toString(),
                "beachinfo-module",
                payload
        );
    }
}