package com.example.testthermofx;

public class Runtime {
    private double actualTemperature;
    private int actualHumidity;

    public Runtime(double actualTemperature, int humidity) {
        this.actualTemperature = actualTemperature;
        this.actualHumidity = humidity;
    }

    public Runtime() {}

    public double getActualTemperature() {
        return actualTemperature;
    }

    public void setActualTemperature(double actualTemperature) {
        this.actualTemperature = actualTemperature;
    }

    public int getActualHumidity() {
        return actualHumidity;
    }

    public void setActualHumidity(int humidity) {
        this.actualHumidity = humidity;
    }
}