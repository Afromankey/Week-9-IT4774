package com.example.testthermofx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammableThermostatSettings {
    private final File settingsFile;

    public ProgrammableThermostatSettings(String filePath) {
        settingsFile = new File(filePath);
    }

    public void saveSettings(List<TemperatureData> settings) {
        try (FileWriter writer = new FileWriter(settingsFile)) {
            for (TemperatureData data : settings) {
                String json = String.format("{\"temperature\": %.2f, \"location\": \"%s\"}\n",
                        data.getTemperature(), data.getLocation());
                writer.write(json);
            }
            System.out.println("Settings saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving settings: " + e.getMessage());
        }
    }

    public List<TemperatureData> loadSettings() {
        List<TemperatureData> settings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                TemperatureData temperatureData = parseTemperatureDataFromJson(line);
                if (temperatureData != null) {
                    settings.add(temperatureData);
                }
            }
            System.out.println("Settings loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading settings: " + e.getMessage());
        }
        return settings;
    }

    private TemperatureData parseTemperatureDataFromJson(String json) {
        try {
            String[] parts = json.split(",");
            double temperature = Double.parseDouble(parts[0].split(":")[1].trim());
            String location = parts[1].split(":")[1].replaceAll("\"", "").trim();
            return new TemperatureData(temperature, location);
        } catch (Exception e) {
            System.out.println("Error parsing temperature data from JSON: " + json);
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\conno\\Documents\\NetBeansProjects\\TestThermo\\settings.txt";
        ProgrammableThermostatSettings settings = new ProgrammableThermostatSettings(filePath);

        // Save settings
        List<TemperatureData> temperatureSettings = new ArrayList<>();
        temperatureSettings.add(new TemperatureData(20.0, "Living Room"));
        temperatureSettings.add(new TemperatureData(18.5, "Bedroom"));
        settings.saveSettings(temperatureSettings);

        // Load settings
        List<TemperatureData> loadedSettings = settings.loadSettings();
        for (TemperatureData data : loadedSettings) {
            System.out.println("Temperature: " + data.getTemperature() + " Location: " + data.getLocation());
        }
    }
}
