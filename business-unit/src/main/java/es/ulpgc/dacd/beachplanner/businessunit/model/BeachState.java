package es.ulpgc.dacd.beachplanner.businessunit.model;

public class BeachState {

    private String beach;
    private double temperature;
    private double wind;
    private double waves;
    private int occupancy;

    public BeachState(String beach, double temperature, double wind, double waves, int occupancy) {
        this.beach = beach;
        this.temperature = temperature;
        this.wind = wind;
        this.waves = waves;
        this.occupancy = occupancy;
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
}