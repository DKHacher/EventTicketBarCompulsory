package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Gui.Models.EventModel;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class EventEditController {
    @FXML
    private TextField titleField, priceField, addressField, cityField, timeField;
    @FXML
    private TextArea descField, extraField;
    @FXML
    private DatePicker datePicker;

    private EventModel eventModel;
    private Event currentEvent;

    public EventEditController() {
        try {
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    private void editEvent(ActionEvent actionEvent) {
        try {
            currentEvent.setEventName(titleField.getText().trim());
            currentEvent.setPrice(new BigDecimal(priceField.getText().trim()));
            currentEvent.setEventDescription(descField.getText().trim());
            currentEvent.setAddress(addressField.getText().trim());
            currentEvent.setCity(cityField.getText().trim());
            currentEvent.setExtraNotes(extraField.getText().trim());
            currentEvent.setDate(datePicker.getValue());
            currentEvent.setEventTime(parseTime(timeField.getText().trim()));

            eventModel.updateEvent(currentEvent);
            eventManagerController.refreshEventTable();
            closeStage(actionEvent);
        } catch (DateTimeParseException e) {
            showAlert("Input Error", "Please enter a valid time in HH:mm format.");
        } catch (Exception e) {
            showAlert("Event Error", "Please check your input fields.");
            e.printStackTrace();
        }
    }

    private LocalTime parseTime(String timeStr) throws DateTimeParseException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeStr, timeFormatter);
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

    public void setEvent(Event event) {
        currentEvent = event;
        titleField.setText(event.getEventName());
        priceField.setText(event.getPrice().toString());
        descField.setText(event.getEventDescription());
        addressField.setText(event.getAddress());
        cityField.setText(event.getCity());
        extraField.setText(event.getExtraNotes());
        datePicker.setValue(event.getDate());
        timeField.setText(event.getEventTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

}
