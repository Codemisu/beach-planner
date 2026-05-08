package es.ulpgc.dacd.beachplanner.businessunit.model;

public class BeachState {

    private String beach;

    private double temperature;
    private double wind;

    private double waves;
    private int occupancy;

    private String skyState;
    private String waveState;
    private int uvIndex;
    private double waterTemperature;

    public BeachState(String beach, double temperature, double wind, double waves, int occupancy) {
        this.beach = beach;
        this.temperature = temperature;
        this.wind = wind;
        this.waves = waves;
        this.occupancy = occupancy;
        this.skyState = "no disponible";
        this.waveState = "no disponible";
        this.uvIndex = 0;
        this.waterTemperature = 0;
    }

    public String getBeach() {
        return beach;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWind() {
        return wind;
    }

    public double getWaves() {
        return waves;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public String getSkyState() {
        return skyState;
    }

    public String getWaveState() {
        return waveState;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public double getWaterTemperature() {
        return waterTemperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public void setWaves(double waves) {
        this.waves = waves;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public void setSkyState(String skyState) {
        this.skyState = skyState;
    }

    public void setWaveState(String waveState) {
        this.waveState = waveState;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public void setWaterTemperature(double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    @Override
    public String toString() {
        return "BeachState{" +
                "beach='" + beach + '\'' +
                ", temperature=" + temperature +
                ", wind=" + wind +
                ", waves=" + waves +
                ", occupancy=" + occupancy +
                ", skyState='" + skyState + '\'' +
                ", waveState='" + waveState + '\'' +
                ", uvIndex=" + uvIndex +
                ", waterTemperature=" + waterTemperature +
                '}';
    }
}