package XML.Gui.Controllers;

import XML.Gui.Models.UserModel;
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

public class EventManagerController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn, genTicketBtn, delPromoType, addPromoType;

    private UserModel userModel;

    public EventManagerController() {
        try {
            userModel = new UserModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    public void initialize() {
        adjustUIForUserRole();
    }

    // FXML Methods (Navigation)
    @FXML
    private void handleAccountButtonAction(ActionEvent actionEvent) {
        accountPane.setVisible(!accountPane.isVisible());
    }
    @FXML
    private void goToDashboard(ActionEvent event) {
        switchScene("/MainPages/Dashboard.fxml", "EASV Bar");
    }
    @FXML
    private void logOut(ActionEvent event) {
        switchScene("/MainPages/LoginPage.fxml", "EASV Bar");
    }
    @FXML
    private void goToTickets(ActionEvent event) {
        switchScene("/MainPages/Tickets.fxml", "EASV Bar");
    }
    @FXML
    private void userManagement(ActionEvent event) {
        switchScene("/MainPages/UserManagement.fxml", "EASV Bar");
    }

    @FXML
    private void eventManagement(ActionEvent event) {
        switchScene("/MainPages/EventManager.fxml", "EASV Bar");
    }


    // Page specific FXML
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

    private void adjustUIForUserRole() {
        try {
            int userRole = userModel.getCurrentUserRole();

            switch (userRole) {
                case 0: // Admin
                    manageUsersBtn.setVisible(true);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    genTicketBtn.setVisible(false);
                    addPromoType.setVisible(false);
                    delPromoType.setVisible(false);
                    break;
                case 1: // Coordinator
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    genTicketBtn.setVisible(true);
                    addPromoType.setVisible(true);
                    delPromoType.setVisible(true);
                    break;
                case 2: // Regular User
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    genTicketBtn.setVisible(false);
                    addPromoType.setVisible(false);
                    delPromoType.setVisible(false);
                    break;
                default:
                    // In case of an undefined role.
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    genTicketBtn.setVisible(false);
                    addPromoType.setVisible(false);
                    delPromoType.setVisible(false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "User Type Error.");
        }
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
            stage.setTitle(title); // Set the stage title
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page.");
            alert.showAndWait();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
