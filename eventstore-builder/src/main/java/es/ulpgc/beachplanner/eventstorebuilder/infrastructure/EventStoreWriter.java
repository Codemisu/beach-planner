package es.ulpgc.beachplanner.eventstorebuilder.infrastructure;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class EventStoreWriter {

    private static final String ROOT = "eventstore";

    private final Gson gson = new Gson();
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String EVENT_STORED_MESSAGE =
            "Event stored in: ";

    public void write(String topic, String eventJson) throws IOException {

        Event event = gson.fromJson(eventJson, Event.class);

        String date = Instant.parse(event.ts())
                .atZone(ZoneOffset.UTC)
                .format(DATE_FORMAT);

        Path directory = Path.of(
                ROOT,
                topic,
                event.ss()
        );

        Files.createDirectories(directory);

        Path file = directory.resolve(date + ".events");

        Files.writeString(
                file,
                eventJson + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        System.out.println(EVENT_STORED_MESSAGE + file);
    }
}