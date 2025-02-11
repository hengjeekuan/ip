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


    @Override
    public String toString() {
        return "[T]" + super.toString();
    }


    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
