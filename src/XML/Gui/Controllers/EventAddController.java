package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Gui.Models.EventModel;
import XML.Gui.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;


public class EventAddController {
    @FXML
    private TextField titleField, priceField, addressField, cityField;
    @FXML
    private TextArea descField, extraField;
    @FXML
    private DatePicker datePicker;

    private EventModel eventModel;

    public EventAddController() {
        try {
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    private void addEvent(ActionEvent actionEvent) {
        try {
            String title = titleField.getText().trim();
            BigDecimal price = new BigDecimal(priceField.getText().trim());
            String description = descField.getText().trim();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            String extraNotes = extraField.getText().trim();
            LocalDate date = datePicker.getValue();

            // Create the Event object
            Event newEvent = new Event(0, date, title, price, city, address, description, extraNotes);
            Event createdEvent = eventModel.createEvent(newEvent);

            if (createdEvent != null) {
                System.out.println("Event Added: " + createdEvent.getEventName());
                closeStage(actionEvent);
                eventManagerController.refreshEventTable();
            } else {
                System.out.println("Failed to add the event for an unknown reason.");
                showAlert("Event Error", "Event could not be added.");
            }
        } catch (Exception e) {
            showAlert("Event Error", "Please check your input fields.");
            System.err.println("Error during event addition: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    private void closeStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private EventManagerController eventManagerController;

    public void setEventManagerController(EventManagerController controller) {
        this.eventManagerController = controller;
    }

}
