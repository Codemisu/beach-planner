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

    public EventStoreReader(String eventStorePath) {
        this.eventStorePath = Paths.get(eventStorePath);
    }

    public List<Event> readHistoricalEvents() {

        List<Event> events = new ArrayList<>();

        if (!Files.exists(eventStorePath)) {
            System.out.println("Event store path does not exist: " + eventStorePath);
            return events;
        }

        try (Stream<Path> files = Files.walk(eventStorePath)) {

            files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".events"))
                    .forEach(path -> readFile(path, events));

        } catch (IOException e) {
            System.out.println("Error reading event store: " + e.getMessage());
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

                        System.out.println("Invalid JSON: " + line);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
        }
    }
}