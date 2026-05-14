package es.ulpgc.dacd.beachplanner.beachinfo.model;
public record BeachInfoRecord(
        String beachName,
        String predictionTime,
        String skyState,
        String windState,
        String waveState,
        String maxTemperature,
        String capturedAt
) {}