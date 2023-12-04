package io.blackjesus.calendario.models;

import io.blackjesus.calendario.enums.CalendarEventType;

import java.time.LocalDate;

public class CalendarEvent {

    private String title, location, description;
    private LocalDate date;
    private CalendarEventType type;
    private boolean isCompleted, isSendNotification;

    public CalendarEvent(String title, LocalDate date, CalendarEventType type, boolean isCompleted) {
        this.type = type;
        this.date = date;
        this.isCompleted = isCompleted;
        this.title = title;
    }

    public CalendarEvent(String title, String location, LocalDate date, CalendarEventType type, boolean isCompleted) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.type = type;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", isCompleted=" + isCompleted +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CalendarEventType getType() {
        return type;
    }

    public void setType(CalendarEventType type) {
        this.type = type;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isSendNotification() {
        return isSendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        isSendNotification = sendNotification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
