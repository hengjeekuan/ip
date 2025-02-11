package jeenius.list;

import jeenius.task.Task;

import java.util.ArrayList;
import java.util.List;

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
        tasks.add(task);
    }

    /**
     * Deletes a task from the list based on its index
     *
     * @param index The index of the task to be removed.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }


}
