package jeenius.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task that extends generic Task class.
 * A Deadline task contains a description, completion by date and time
 * and completion status.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Creates a Deadline task with the input description
     * and completion by date and time.
     * Task is initially marked as not done.
     *
     * @param description Textual description of the Deadline task.
     * @param by Deadline's completion by date and time in "d/M/yyyy HHmm" format
     */
    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        this.by = LocalDateTime.parse(by, formatter);
    }

    public String getBy() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return by.format(formatter);
    }

    /**
     * Returns a string representation of the Deadline task,
     * including its type identifier and completion status
     *
     * @return A formatted string representing the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Converts the Deadline task into a standardized format for file storage.
     *
     * @return A string representing the Deadline task in file format storage
     */
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + getBy();
    }
}
