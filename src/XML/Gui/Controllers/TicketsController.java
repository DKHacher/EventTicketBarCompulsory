package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Be.PromoTicket;
import XML.Be.Ticket;
import XML.Be.User;
import XML.Gui.Models.EventModel;
import XML.Gui.Models.TicketModel;
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
import java.util.List;

public class TicketsController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn, genTicketBtn, delPromoType, addPromoType, delTicketBtn;

    private UserModel userModel;
    private EventModel eventModel;
    private TicketModel ticketModel;
    @FXML
    private TableColumn ticketTypeCol;
    @FXML
    private TableColumn ticketEventCol;
    @FXML
    private TableColumn ticketCoordinatorCol;
    @FXML
    private TableColumn ticketOwnerCol;
    @FXML
    private TableView<PromoTicket> tblPromoTickets;
    @FXML
    private TableColumn promoTypeCol;
    @FXML
    private TableColumn promoDescriptionCol;
    @FXML
    private TableColumn eventDateCol;
    @FXML
    private TableColumn eventTitleCol;
    @FXML
    private TableColumn eventPriceCol;
    @FXML
    private TableColumn eventCityCol;
    @FXML
    private TableColumn eventAddressCol;
    @FXML
    private TableColumn eventDescCol;
    @FXML
    private TableColumn eventExtraCol;
    @FXML
    private TableView tblUpcomingEvents;
    @FXML
    private TableView<Ticket> tblTickets;

    public TicketsController() {
        try {
            userModel = new UserModel();
            eventModel = new EventModel();
            ticketModel = new TicketModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    public void initialize() {
        adjustUIForUserRole();
        loadPromoTickets();
        loadTickets();
        loadUpcomingEvents();
        setupUpcomingEventTableColumns();
        setupTicketTableColumns();
        setupPromoTicketTableColumns();
    }
    private void setupUpcomingEventTableColumns() {
        eventDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        eventTitleCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        eventCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        eventAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        eventDescCol.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        eventExtraCol.setCellValueFactory(new PropertyValueFactory<>("extraNotes"));
    }
    private void setupTicketTableColumns() {
        ticketTypeCol.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        ticketEventCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        ticketCoordinatorCol.setCellValueFactory(new PropertyValueFactory<>("coordinatorId"));
        ticketOwnerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
    private void setupPromoTicketTableColumns() {
        promoTypeCol.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        promoDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("ticketDescription"));
    }
    private void loadUpcomingEvents() {
        try {
            tblUpcomingEvents.getItems().clear();
            List<Event> events = eventModel.getAllEvents();
            System.out.println("Number of events loaded: " + events.size());
            if (events.isEmpty()) {
                System.out.println("No events to display.");
            }
            for (Event event : events) {
                tblUpcomingEvents.getItems().add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load event data.");
        }
    }

    private void loadPromoTickets() {
        try {
            tblPromoTickets.getItems().clear();
            List<PromoTicket> promoTickets = ticketModel.getAllPromoTickets();
            System.out.println("Number of promos loaded: " + promoTickets.size());
            if (promoTickets.isEmpty()) {
                System.out.println("No promo tickets to display.");
            }
            for (PromoTicket ticket : promoTickets) {
                tblPromoTickets.getItems().add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load promo ticket data.");
        }
    }
    private void loadTickets() {
        try {
            tblTickets.getItems().clear();
            List<Ticket> tickets = ticketModel.getAllTickets();
            System.out.println("Number of tickets loaded: " + tickets.size());
            if (tickets.isEmpty()) {
                System.out.println("No tickets to display.");
            }
            for (Ticket ticket: tickets) {
                tblTickets.getItems().add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load ticket data.");
        }
    }

    public void refreshPromoTicketTable() {
        loadPromoTickets();
    }
    public void refreshTicketTable() {
        loadTickets();
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
    private void handleGenerateTicket(ActionEvent actionEvent) {
    }

    @FXML
    private void handleNewPromoType(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pop-Ups/PromoAdd.fxml"));
            Parent root = loader.load();

            PromoAddController addController = loader.getController();
            addController.setEventManagerController(this);  // Pass the reference

            Stage newEventStage = new Stage();
            newEventStage.initModality(Modality.APPLICATION_MODAL);
            newEventStage.setTitle("Add New PromoTicket");
            newEventStage.setScene(new Scene(root));
            newEventStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Could not load the Add Event page");
        }
    }

    @FXML
    private void handleDeletePromoType(ActionEvent actionEvent) {
        PromoTicket selectedPromoTicket = tblPromoTickets.getSelectionModel().getSelectedItem();
        if (selectedPromoTicket == null)
        {
            showError("No Selection", "No event selected. Please select an event to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected event?");
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Event");
        confirmAlert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {
                try
                {
                    ticketModel.deletePromoTicket(selectedPromoTicket);
                    refreshPromoTicketTable();
                    showAlert("Deletion Successful", "The event has been successfully deleted.");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    showError("Error", "Could not delete the event.");
                }
            }
        });
    }

    @FXML
    private void handleDeleteTicket(ActionEvent actionEvent) {
        Ticket seletedTicket = tblTickets.getSelectionModel().getSelectedItem();
        if (seletedTicket == null)
        {
            showError("No Selection", "No event selected. Please select an event to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected event?");
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Event");
        confirmAlert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {
                try
                {
                    ticketModel.deleteTicket(seletedTicket);
                    refreshTicketTable();
                    showAlert("Deletion Successful", "The event has been successfully deleted.");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    showError("Error", "Could not delete the event.");
                }
            }
        });
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
                    delTicketBtn.setVisible(false);
                    break;
                case 1: // Coordinator
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(true);
                    dashboardBtn.setVisible(true);
                    ticketsBtn.setVisible(true);
                    genTicketBtn.setVisible(true);
                    addPromoType.setVisible(true);
                    delPromoType.setVisible(true);
                    delTicketBtn.setVisible(true);
                    break;
                case 2: // Regular User
                    manageUsersBtn.setVisible(false);
                    eventBtn.setVisible(false);
                    ticketsBtn.setVisible(false);
                    dashboardBtn.setVisible(true);
                    genTicketBtn.setVisible(false);
                    addPromoType.setVisible(false);
                    delPromoType.setVisible(false);
                    delTicketBtn.setVisible(false);
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
                    delTicketBtn.setVisible(false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "User Type Error.");
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
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
            stage.setTitle(title);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the page: " + title);
        }
    }



}
