package es.ulpgc.dacd.beachplanner.beachinfo.model;

public class BeachInfoRecord {
    private final String beachName;
    private final String predictionTime;
    private final String skyState;
    private final String windState;
    private final String waveState;
    private final String maxTemperature;
    private final String capturedAt;

    public BeachInfoRecord(String beachName, String predictionTime, String skyState,
                           String windState, String waveState, String maxTemperature,
                           String capturedAt) {
        this.beachName = beachName;
        this.predictionTime = predictionTime;
        this.skyState = skyState;
        this.windState = windState;
        this.waveState = waveState;
        this.maxTemperature = maxTemperature;
        this.capturedAt = capturedAt;
    }

    public String getBeachName() {
        return beachName;
    }

    public String getPredictionTime() {
        return predictionTime;
    }

    public String getSkyState() {
        return skyState;
    }

    public String getWindState() {
        return windState;
    }

    public String getWaveState() {
        return waveState;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getCapturedAt() {
        return capturedAt;
    }
}