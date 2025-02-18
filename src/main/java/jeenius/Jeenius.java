package jeenius;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
public class Jeenius extends Application {
    private static final String DEFAULT_FILE_PATH = "data/Jeenius.txt";
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;
    private final Parser parser;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/IMG_0134.JPG"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/IMG_2354.JPG"));
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
            loadedTasks = new TaskList(new ArrayList<Task>());
        }
        this.tasks = loadedTasks;
    }

    public Jeenius() {
        this(DEFAULT_FILE_PATH);
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
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        dialogContainer.getChildren().addAll(new DialogBox(userInput.getText(), userImage));
        userInput.clear();
    }


}
