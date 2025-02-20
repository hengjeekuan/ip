package jeenius;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Jeenius jeenius;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/IMG_0134.JPG"));
    private Image jeeniusImage = new Image(this.getClass().getResourceAsStream("/images/IMG_2354.JPG"));

    /**
     * Initializes the main window of the Jeenius application
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String welcomeMessage = "----------------------------------------\n"
                + "Hello! I'm Jeenius\n"
                + "What can I do for you today?\n"
                + "----------------------------------------";
        dialogContainer.getChildren().add(DialogBox.getJeeniusDialog(welcomeMessage, jeeniusImage));
    }

    /** Injects the Jeenius instance */
    public void setJeenius(Jeenius j) {
        jeenius = j;
    }

    /**
     * Handles user input when enter key is pressed or the send button is clicked.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        String response = jeenius.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJeeniusDialog(response, jeeniusImage)
        );
        userInput.clear();
    }
    /**
     * Handles sorting of tasks when sort button is clicked.
     */
    @FXML
    private void handleSortButtonClick() {
        String response = jeenius.getResponse("sort");
        dialogContainer.getChildren().add(DialogBox.getJeeniusDialog(response, jeeniusImage));
    }

}
