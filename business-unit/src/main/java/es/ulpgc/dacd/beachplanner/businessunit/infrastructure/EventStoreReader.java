package es.ulpgc.dacd.beachplanner.businessunit.infrastructure;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;

public class EventStoreReader {

    private final Path eventStorePath;
    private final Gson gson = new Gson();
    private static final String EVENTS_EXTENSION =
            ".events";

    private static final String PATH_NOT_EXISTS_MESSAGE =
            "Event store path does not exist: ";

    private static final String ERROR_READING_STORE_MESSAGE =
            "Error reading event store: ";

    private static final String INVALID_JSON_MESSAGE =
            "Invalid JSON: ";

    private static final String ERROR_READING_FILE_MESSAGE =
            "Error reading file ";


    public EventStoreReader(String eventStorePath) {
        this.eventStorePath = Paths.get(eventStorePath);
    }

    public List<Event> readHistoricalEvents() {

        List<Event> events = new ArrayList<>();

        if (!Files.exists(eventStorePath)) {
            System.out.println(PATH_NOT_EXISTS_MESSAGE + eventStorePath);
            return events;
        }

        try (Stream<Path> files = Files.walk(eventStorePath)) {

            files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(EVENTS_EXTENSION))
                    .forEach(path -> readFile(path, events));

        } catch (IOException e) {
            System.out.println(ERROR_READING_STORE_MESSAGE + e.getMessage());
        }

        return events;
    }

    private void readFile(Path filePath, List<Event> events) {

        try {

            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {

                if (!line.isBlank()) {

                    try {



                        Event event = gson.fromJson(line, Event.class);

                        events.add(event);

                    } catch (Exception e) {

                        System.out.println(INVALID_JSON_MESSAGE + line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(
                    ERROR_READING_FILE_MESSAGE
                            + filePath
                            + ": "
                            + e.getMessage()
            );
        }
    }
}