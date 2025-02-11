package jeenius.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task that extends generic Task class.
 * A Event task contains a description, start and end date and time,
 * and completion status.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates an Event task with the input description
     * start date and time, and end date and time.
     * Task is initially marked as not done.
     *
     * @param description Textual description of the Event task.
     * @param from Event's start date and time in "d/M/yyyy HHmm" format
     * @param to Event's end date and time in "d/M/yyyy HHmm" format
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        this.from = LocalDateTime.parse(from, formatter);
        this.to = LocalDateTime.parse(to, formatter);
    }

    public String getFrom() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return from.format(formatter);
    }

    public String getTo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return to.format(formatter);
    }

    /**
     * Returns a string representation of the Event task,
     * including its type identifier and completion status
     *
     * @return A formatted string representing the Event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Converts the Event task into a standardized format for file storage.
     *
     * @return A string representing the Event task in file format storage
     */
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + getFrom() + " | " + getTo();
    }
}
