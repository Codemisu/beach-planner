package es.ulpgc.dacd.beachplanner.businessunit.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EventStoreReader {

    private final Path eventStorePath;

    public EventStoreReader(String eventStorePath) {
        this.eventStorePath = Paths.get(eventStorePath);
    }

    public List<String> readHistoricalEvents() {
        List<String> events = new ArrayList<>();

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

    private void readFile(Path filePath, List<String> events) {
        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                if (!line.isBlank()) {
                    events.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
        }
    }
}