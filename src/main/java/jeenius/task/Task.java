package jeenius.task;

/**
 * Represents a generic task with a description and completion status.
 * Serves as parent class for specific task types (Todo, Deadline, Event).
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with input description.
     * Task is initially marked as not done.
     *
     * @param description Textual description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] "+ this.getDescription();
    }

    /**
     * Converts the task to a standardized format for file storage.
     * This format should be implemented by subclasses to ensure consistency
     * in saving and loading tasks from files.
     *
     * @return A formatted string representing the task, suitable for file storage
     */
    public abstract String toFileFormat();
}
