package jeenius.ui;

import jeenius.task.Task;

import java.util.List;

public class Ui {

    public void printWelcomeMessage() {
        printLine();
        System.out.println("Hello! I'm Jeenius");
        System.out.println("What can I do for you today?");
        printLine();
    }

    public void printExitMessage() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public void printLine() {
        System.out.println("----------------------------------------");
    }

    public void printError(String message) {
        System.out.println(message);
    }

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
