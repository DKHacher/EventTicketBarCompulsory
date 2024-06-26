package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Gui.Models.EventModel;
import XML.Gui.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn;
    @FXML
    private HBox eventsHBox;
    @FXML
    private Label highlightTitleLabel, highlightBodyLabel;
    @FXML
    private ImageView highlightImage;

    private Event highlightedEvent;
    private UserModel userModel;
    private EventModel eventModel;

    public DashboardController() {
        try {
            userModel = new UserModel();
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    public void initialize() {
        adjustUIForUserRole();
        loadEventsHBox();
        loadRandomEventHighlight();
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

    @FXML
    private void readMore(ActionEvent event) {
        if (highlightedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPages/EventPage.fxml"));
                Parent root = loader.load();

                EventPageController eventPageController = loader.getController();
                eventPageController.setEventData(highlightedEvent);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(highlightedEvent.getEventName());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load the event details page.");
            }
        } else {
            showAlert("Notice", "No event is currently highlighted.");
        }
    }


    // Page specific FXML



    // Other Methods
    private void adjustUIForUserRole() {
        try {
            int userRole = userModel.getCurrentUserRole();
            System.out.println("Adjusting UI for user role: " + userRole);


            switch (userRole) {
                case 0: // Admin
                    manageUsersBtn.setVisible(true);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    break;
                case 1: // Coordinator
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    break;
                case 2: // Regular User
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    break;
                default:
                    // In case of an undefined role.
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "User Type Error.");
        }
    }


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
            stage.setTitle(title); // Set the stage title
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the page: " + title);
        }
    }


    // Event Loading Methods
    private void loadEventsHBox() {
        try {
            List<Event> events = eventModel.getAllEvents();
            for (Event event : events) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pop-ups/EventItem.fxml"));
                    Node eventItem = loader.load();

                    EventItemController controller = loader.getController();
                    controller.setEventData(event);

                    eventItem.setOnMouseClicked(e -> controller.onEventItemClicked(e));

                    eventsHBox.getChildren().add(eventItem);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Could not load event item.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load events data.");
        }
    }

    private void loadRandomEventHighlight() {
        try {
            List<Event> events = eventModel.getAllEvents();
            if (!events.isEmpty()) {
                highlightedEvent = events.get(new java.util.Random().nextInt(events.size()));
                highlightTitleLabel.setText(highlightedEvent.getEventName());
                highlightBodyLabel.setText(highlightedEvent.getEventDescription());
                File file = new File(highlightedEvent.getFilePath());
                if (file.exists()) {
                    highlightImage.setImage(new Image(file.toURI().toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load random event highlight.");
        }
    }




}
