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
    public void printWelcomeMessage() {
        printLine();
        System.out.println("Hello! I'm Jeenius");
        System.out.println("What can I do for you today?");
        printLine();
    }

    /**
     * Prints the exit message when the application is terminated.
     */
    public void printExitMessage() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints a horizontal line for formatting outputs.
     */
    public void printLine() {
        System.out.println("----------------------------------------");
    }

    /**
     * Prints an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void printError(String message) {
        System.out.println(message);
    }

    /**
     * Prints the current task list with numbering.
     *
     * @param storage The list of tasks to be displayed.
     */
    public void printTaskList(List<Task> storage) {
        printLine();
        System.out.println("Task List:");
        for (int x = 0; x < storage.size(); x = x + 1) {
            int num = x + 1;
            Task input = storage.get(x);
            System.out.println(num + "." + input.toString());
        }
        printLine();
    }
}
