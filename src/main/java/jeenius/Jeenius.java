package jeenius;

import jeenius.command.Parser;
import jeenius.exception.JeeniusException;
import jeenius.list.TaskList;
import jeenius.storage.Storage;
import jeenius.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class for the Jeenius application.
 * Initializes the necessary components and handles user interactions.
 */
public class Jeenius {
    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;

    /**
     * Creates an instance of Jeenius with the specified storage file path.
     * @param filePath The file path for storing tasks.
     */
    public Jeenius(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.parser = new Parser();
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (JeeniusException e) {
            ui.printError(e.getMessage());
            loadedTasks = new TaskList(new ArrayList<>());
        }
        this.tasks = loadedTasks;
    }

    /**
     * Runs the Jeenius application, processing user commands in a loop.
     */
    public void run() {
        ui.printWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            String input = scanner.nextLine();
            try {
                parser.parse(input, tasks, ui, storage);
                if (input.equalsIgnoreCase("bye")) {
                    isExit = true;
                }
            } catch (JeeniusException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the Jeenius application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new Jeenius("data/Jeenius.txt").run();
    }


}
