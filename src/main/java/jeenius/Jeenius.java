package jeenius;

import java.util.ArrayList;
import java.util.Scanner;

import jeenius.command.Parser;
import jeenius.exception.JeeniusException;
import jeenius.list.TaskList;
import jeenius.storage.Storage;
import jeenius.task.Task;
import jeenius.ui.Ui;



/**
 * The main class for the Jeenius application.
 * Initializes the necessary components and handles user interactions.
 */
public class Jeenius {
    private static final String DEFAULT_FILE_PATH = "data/Jeenius.txt";
    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;
    /**
     * Creates an instance of Jeenius with the specified storage file path.
     * @param filePath The file path for storing tasks.
     */
    public Jeenius(String filePath) {
        assert filepath != null && !filePath.isEmpty() : "File path must not be null or empty";
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.parser = new Parser();
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (JeeniusException e) {
            ui.printError(e.getMessage());
            loadedTasks = new TaskList(new ArrayList<Task>());
        }
        this.tasks = loadedTasks;

        assert this.ui != null : "Ui instance should not be null";
        assert this.storage != null : "Storage instance should not be null";
        assert this.parser != null : "Parser instance should not be null";
        assert this.tasks != null : "TaskList should not be null";
    }

    public Jeenius() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Runs the Jeenius application, processing user commands in a loop.
     */
    public void run() {
        assert ui != null : "Ui should not be null before running";
        assert parser != null : "Parser should not be null before running";
        ui.printWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            String input = scanner.nextLine();
            assert input != null : "User input should not be null";
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

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        assert input != null : "User input should not be null";
        return "Duke heard: " + input;
    }

}
