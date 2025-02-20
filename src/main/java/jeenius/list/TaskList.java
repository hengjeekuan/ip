package jeenius.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jeenius.task.Deadline;
import jeenius.task.Event;
import jeenius.task.Task;
import jeenius.task.ToDo;

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

    /**
     * Sorts the tasks in the task list based on their dates
     */
    public void sortTasks() {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                if (t1 instanceof Deadline && t2 instanceof Deadline) {
                    return ((Deadline) t1).getBy().compareTo(((Deadline) t2).getBy());
                }
                if (t1 instanceof Event && t2 instanceof Event) {
                    int startComparison = ((Event) t1).getFrom().compareTo(((Event) t2).getFrom());
                    if (startComparison != 0) {
                        return startComparison;
                    }
                    return ((Event) t1).getTo().compareTo(((Event) t2).getTo());
                }
                if (t1 instanceof Deadline && t2 instanceof Event) {
                    int result =  ((Deadline) t1).getBy().compareTo(((Event) t2).getFrom());
                    return result != 0 ? result : -1;
                }
                if (t1 instanceof Event && t2 instanceof Deadline) {
                    int result = ((Event) t1).getFrom().compareTo(((Deadline) t2).getBy());
                    return result != 0 ? result : 1;
                }

                if (t1 instanceof ToDo && !(t2 instanceof ToDo)) {
                    return 1;
                }
                if (t2 instanceof ToDo && !(t1 instanceof ToDo)) {
                    return -1;
                }
                return 0;
            }
        });
    }
}
