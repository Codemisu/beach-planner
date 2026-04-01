package es.ulpgc.dacd.beachplanner.beachinfo.model;

public class BeachInfoRecord {
    private final String beachName;
    private final String municipality;
    private final String province;
    private final String predictionTime;
    private final String skyState;
    private final String windState;
    private final String waveState;
    private final String waterTemperature;
    private final String uvIndex;
    private final String capturedAt;

    public BeachInfoRecord(String beachName, String municipality, String province,
                           String predictionTime, String skyState, String windState,
                           String waveState, String waterTemperature, String uvIndex,
                           String capturedAt) {
        this.beachName = beachName;
        this.municipality = municipality;
        this.province = province;
        this.predictionTime = predictionTime;
        this.skyState = skyState;
        this.windState = windState;
        this.waveState = waveState;
        this.waterTemperature = waterTemperature;
        this.uvIndex = uvIndex;
        this.capturedAt = capturedAt;
    }

    public String getBeachName() { return beachName; }
    public String getMunicipality() { return municipality; }
    public String getProvince() { return province; }
    public String getPredictionTime() { return predictionTime; }
    public String getSkyState() { return skyState; }
    public String getWindState() { return windState; }
    public String getWaveState() { return waveState; }
    public String getWaterTemperature() { return waterTemperature; }
    public String getUvIndex() { return uvIndex; }
    public String getCapturedAt() { return capturedAt; }
}