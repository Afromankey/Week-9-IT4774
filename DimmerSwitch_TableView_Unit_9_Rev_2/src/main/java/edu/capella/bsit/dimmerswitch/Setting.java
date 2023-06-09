package edu.capella.bsit.dimmerswitch;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Setting {
    @JsonProperty("day")
    private int dayOfWeek;
    private LocalTime time;
    private int level;
    private String holiday; // Added for holiday feature

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

    // Added setter and getter for holiday
    public void setHoliday(String holiday) {
    	this.holiday = holiday;
    }

    public String getHoliday() {
    	return holiday;
    }

    @Override
    public String toString() {
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        return "Setting: " + dayNames[dayOfWeek] + ", " + dateTimeFormatter.format(time) +
                ", " + level + "%";
    }
}
