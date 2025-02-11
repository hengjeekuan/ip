package jeenius.task;

/**
 * Represents a ToDo task that extends generic Task class
 * A ToDo task contains a description and completion status
 */
public class ToDo extends Task {
    /**
     * Creates a ToDo task with the input description.
     * Task is initially marked as not done.
     *
     * @param description Textual description of the ToDo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task,
     * including its type identifier and completion status
     *
     * @return A formatted string representing the ToDo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts the ToDo task into a standardized format for file storage.
     *
     * @return A string representing the ToDo task in file format storage
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
