package ru.javawebinar.topjava.model;

public class MealToView {
    private final Integer id;
    private final String formattedDateTime;
    private final String description;
    private final int calories;
    private final boolean excess;

    public MealToView(Integer id, String formattedDateTime, String description,
                      int calories, boolean excess) {
        this.id = id;
        this.formattedDateTime = formattedDateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }
}
