package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractNamedEntity {
    private int userId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(int userId, LocalDateTime dateTime, String description, int calories) {
        this(userId, null, dateTime, description, calories);
    }

    public Meal(int userId, Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id, description);
        this.description = description;
        this.userId = userId;
        this.dateTime = dateTime;
        this.calories = calories;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return super.toString() +
                "{dateTime=" + dateTime +
                ", calories=" + calories +
                '}';
    }
}
