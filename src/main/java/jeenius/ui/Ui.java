package jeenius.ui;

import java.util.List;

import jeenius.task.Task;

/**
 * Handles user interactions by displaying messages and formatting outputs.
 */
public class Ui {
    /**
     * Prints the welcome message at the start of the application,
     */
    public String printWelcomeMessage() {
        return getDivider() + "\nHello! I'm Jeenius\nWhat can I do for you today?\n" + getDivider();
    }

    /**
     * Prints the exit message when the application is terminated.
     */
    public String printExitMessage() {
        return getDivider() + "\nBye. Hope to see you again soon!\n" + getDivider();
    }

    /**
     * Prints a horizontal line for formatting outputs.
     */
    public String getDivider() {
        return "----------------------------------------";
    }

    /**
     * Prints an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public String printError(String message) {
        return message;
    }

    /**
     * Prints the current task list with numbering.
     *
     * @param storage The list of tasks to be displayed.
     */
    public String printTaskList(List<Task> storage) {
        if (storage.isEmpty()) {
            return "Your task list is empty!";
        }
        StringBuilder response = new StringBuilder(getDivider()).append("\nTask List:\n");
        for (int i = 0; i < storage.size(); i++) {
            response.append(i + 1).append(". ").append(storage.get(i)).append("\n");
        }
        response.append(getDivider());
        return response.toString();
    }
}
