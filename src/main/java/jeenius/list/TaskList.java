package jeenius.list;

import java.util.ArrayList;
import java.util.List;

import jeenius.task.Task;

/**
 * Manages a list of tasks, providing operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates a TaskList with an initial set of tasks.
     *
     * @param tasks The initial list of tasks. If null, an empty list is created.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
        assert this.tasks != null : "Task list should never be null";
    }
    /**
    * Finds and returns a list of tasks that contain the specified keyword.
    *
    * @param keyword The keyword to search for within task descriptions.
    * @return A list of tasks that contain the given keyword (case-insensitive).
    */
    public List<Task> findTasks(String keyword) {
        assert keyword != null : "Keyword should not be null";

        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
    /**
     * Adds a new task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Task should not be null";
        tasks.add(task);
    }

    /**
     * Deletes a task from the list based on its index
     *
     * @param index The index of the task to be removed.
     */
    public void deleteTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        tasks.remove(index);
    }

    public Task getSize(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }


}
