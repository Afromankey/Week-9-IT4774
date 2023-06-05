package com.example.testthermofx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class DataTools {
    public static ArrayList<Setting> readFileData() {
        ArrayList<Setting> settings = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try (var jsonReader = Files.newBufferedReader(Path.of("dimmer.json"))) {
            String jsonData;
            while ((jsonData = jsonReader.readLine()) != null) {
                Setting setting = objectMapper.readValue(jsonData, Setting.class);
                settings.add(setting);
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }
        return settings;
    }

public static void writeFileData(Setting setting) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Write the setting to the file. Create file if it doesn't exist,
        // append if it does.
        try (var jsonWriter = Files.newBufferedWriter(Path.of("dimmer.json"),
            StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            // Convert to JSON and write to file in separate steps, so
            // we can write a newline after each JSON object.
            var json = objectMapper.writeValueAsString(setting);
            jsonWriter.write(json);
            jsonWriter.newLine();
        } catch (IOException ex) {
            System.out.println("Error writing file: " + ex.getMessage());
        }
    }
}
