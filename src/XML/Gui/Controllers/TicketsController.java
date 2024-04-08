package XML.Gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TicketsController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn;


    // FXML Methods (Navigation)
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
        switchScene("/Tickets.fxml", "EASV Bar");
    }
    @FXML
    private void userManagement(ActionEvent event) {
        switchScene("/UserManagement.fxml", "EASV Bar");
    }

    @FXML
    private void eventManagement(ActionEvent event) {
        switchScene("/EventManager.fxml", "EASV Bar");
    }


    // Page specific FXML
    @FXML
    private void handleGenerateTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleNewPromoType(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDeletePromoType(ActionEvent actionEvent) {
    }

    @FXML
    private void handleDeleteTicket(ActionEvent actionEvent) {
    }


    // Other Methods
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void switchScene(String fxmlPath, String title) {
        Stage stage = (Stage) accountPane.getScene().getWindow();

        try {
            // Load the new scene from the specified FXML path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the page: " + title);
        }
    }



}
