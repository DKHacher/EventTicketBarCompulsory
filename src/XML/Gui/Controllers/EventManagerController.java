package XML.Gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class EventManagerController {
    @FXML
    private Pane accountPane;



    // FXML Methods
    @FXML
    private void handleAccountButtonAction(ActionEvent actionEvent) {
        accountPane.setVisible(!accountPane.isVisible());
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        switchScene("/MainPageAdmin.fxml", "EASV Bar");
    }

    @FXML
    private void logOut(ActionEvent event) {
        switchScene("/LoginPage.fxml", "EASV Bar");
    }

    @FXML
    private void goToTickets(ActionEvent event) {
        // switchScene("/ticketsManagement.fxml", "EASV Bar");
    }

    @FXML
    private void handleDeleteEvent(ActionEvent event) {
        //deleteEvent();
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        //addEvent();
    }

    @FXML
    private void handleEditEvent(ActionEvent event) {
        //editEvent();
    }

    @FXML
    private void handleHighlightEvent(ActionEvent event) {
        //highlightEvent();
    }

    // Other Methods


    private void switchScene(String fxmlPath, String title) {
        Stage stage = (Stage) accountPane.getScene().getWindow();

        try {
            // Load the new scene from the specified FXML path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

            stage.setScene(scene);
            stage.setTitle(title); // Set the stage title
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page.");
            alert.showAndWait();
        }
    }
}
