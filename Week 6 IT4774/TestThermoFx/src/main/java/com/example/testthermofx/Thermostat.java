package com.example.testthermofx;

public class Thermostat {
    private String identifier;
    private String name;
    private String thermostatTime;
    private String utcTime;
    private Runtime runtime;
    private Status status;

    public Thermostat(String identifier, String name, String thermostatTime,
                      String utcTime, Runtime runtime, Status status) {
        this.identifier = identifier;
        this.name = name;
        this.thermostatTime = thermostatTime;
        this.utcTime = utcTime;
        this.runtime = runtime;
        this.status = status;
    }

    public Thermostat() {}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThermostatTime() {
        return thermostatTime;
    }

    public void setThermostatTime(String thermostatTime) {
        this.thermostatTime = thermostatTime;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }

    public Runtime getRuntime() {
        return runtime;
    }

    public void setRuntime(Runtime runtime) {
        this.runtime = runtime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

