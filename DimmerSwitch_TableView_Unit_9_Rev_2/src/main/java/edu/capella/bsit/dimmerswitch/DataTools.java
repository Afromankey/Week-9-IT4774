package edu.capella.bsit.dimmerswitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataTools {
    public static ArrayList<Setting> readFileData(Path path) {
        ArrayList<Setting> settings = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try(var jsonReader = Files.newBufferedReader(path)) {
            String jsonData;
            while((jsonData = jsonReader.readLine()) != null) {
                Setting setting = mapper.readValue(jsonData, Setting.class);
                settings.add(setting);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return settings;
    }

    public static void writeFileData(Path path, Setting setting) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try(var jsonWriter = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            var json = objectMapper.writeValueAsString(setting);
            jsonWriter.write(json);
            jsonWriter.newLine();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }

    public static void updateFile(Path path, List<Setting> settings) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try(var jsonWriter = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for(Setting s : settings) {
                var json = objectMapper.writeValueAsString(s);
                jsonWriter.write(json);
                jsonWriter.newLine();
            }
        }
        catch(IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }
}
