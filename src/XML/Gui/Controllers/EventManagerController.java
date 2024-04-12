package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Gui.Models.EventModel;
import XML.Gui.Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EventManagerController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn, addEventBtn, editEventBtn, highlightEventBtn, delEventBtn;
    @FXML
    private TableView<Event> upcomingTableView, pastTableView;
    @FXML
    private TableColumn<Event, String> titleCol, cityCol, addressCol, descCol, extraCol;
    @FXML
    private TableColumn<Event, BigDecimal> priceCol;
    @FXML
    private TableColumn<Event, LocalDate> dateCol;

    private EventModel eventModel;
    private UserModel userModel;

    public EventManagerController() {
        try {
            userModel = new UserModel();
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unknown Error.");
        }
    }

    @FXML
    public void initialize() {
        adjustUIForUserRole();
        setupEventTableColumns();
        loadEvents();
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
        Event selectedEvent = upcomingTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showError("No Selection", "No event selected. Please select an event to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected event?");
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Event");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    eventModel.deleteEvent(selectedEvent);
                    refreshEventTable();
                    showAlert("Deletion Successful", "The event has been successfully deleted.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error", "Could not delete the event.");
                }
            }
        });
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pop-Ups/EventAdd.fxml"));
            Parent root = loader.load();

            EventAddController addController = loader.getController();
            addController.setEventManagerController(this);  // Pass the reference

            Stage newEventStage = new Stage();
            newEventStage.initModality(Modality.APPLICATION_MODAL);
            newEventStage.setTitle("Add New Event");
            newEventStage.setScene(new Scene(root));
            newEventStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not load the Add Event page");
        }
    }


    @FXML
    private void handleEditEvent(ActionEvent event) {
        if (upcomingTableView.getSelectionModel().getSelectedItem() == null) {
            showError("No Selection", "No event selected. Please select an event to edit.");
            return;
        }
        Event selectedEvent = upcomingTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pop-Ups/EventEdit.fxml"));
            Parent root = loader.load();

            EventEditController editController = loader.getController();
            editController.setEvent(selectedEvent);  // Pass the selected event
            editController.setEventManagerController(this);

            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setTitle("Edit Event");
            editStage.setScene(new Scene(root));
            editStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not load the Edit Event page");
        }
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
                    addEventBtn.setVisible(false);
                    editEventBtn.setVisible(false);
                    highlightEventBtn.setVisible(false);
                    delEventBtn.setVisible(false);
                    break;
                case 1: // Coordinator
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    addEventBtn.setVisible(true);
                    editEventBtn.setVisible(true);
                    highlightEventBtn.setVisible(true);
                    delEventBtn.setVisible(true);
                    break;
                case 2: // Regular User
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    addEventBtn.setVisible(false);
                    editEventBtn.setVisible(false);
                    highlightEventBtn.setVisible(false);
                    delEventBtn.setVisible(false);
                    break;
                default:
                    // In case of an undefined role.
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    addEventBtn.setVisible(false);
                    editEventBtn.setVisible(false);
                    highlightEventBtn.setVisible(false);
                    delEventBtn.setVisible(false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "User Type Error.");
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

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void setupEventTableColumns() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        extraCol.setCellValueFactory(new PropertyValueFactory<>("extraNotes"));
    }

    private void loadEvents() {
        try {
            List<Event> events = eventModel.getAllEvents();
            System.out.println("Number of events loaded: " + events.size());
            ObservableList<Event> eventData = FXCollections.observableArrayList(events);
            if (eventData.isEmpty()) {
                System.out.println("No events to display.");
            }
            upcomingTableView.setItems(eventData);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Could not load event data.");
        }
    }

    public void refreshEventTable() {
        loadEvents();
    }
}
