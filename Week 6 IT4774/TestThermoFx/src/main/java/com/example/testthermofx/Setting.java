package com.example.testthermofx;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Setting {
    @JsonProperty("day")
    private int dayOfWeek;
    private LocalTime time;
    private int level;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public Setting(int dayOfWeek, LocalTime time, int level) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.level = level;
    }

    // Need default constructor for Jackson
    public Setting() { }
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        return "Setting: " + dayNames[dayOfWeek] + ", " + dateTimeFormatter.format(time) +
                ", " + level + "%";
    }
}
