package com.example.testthermofx;

public class TemperatureData {
    private double temperature;
    private String location;

    public TemperatureData(double temperature, String location) {
        this.temperature = temperature;
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }
}
