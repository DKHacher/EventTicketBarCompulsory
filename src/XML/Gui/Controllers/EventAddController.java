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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class EventAddController {
    @FXML
    private TextField titleField, priceField, addressField, cityField, timeField, imageField;
    @FXML
    private TextArea descField, extraField;
    @FXML
    private DatePicker datePicker;

    private EventModel eventModel;
    private Path imagePath;

    public EventAddController() {
        try {
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image for Event");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destPath = Paths.get("Resources/Images/EventAssets/" + selectedFile.getName());
                Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destPath; // Store the path to the copied image
                imageField.setText(imagePath.toString()); // Show path in TextField
            } catch (IOException e) {
                showAlert("File Error", "Failed to save the image.");
                e.printStackTrace();
            }
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
            LocalTime time = parseTime(timeField.getText().trim());
            String imagePathStr = (imagePath != null) ? imagePath.toString() : ""; // Get path as string

            Event newEvent = new Event(0, date, title, price, city, address, description, extraNotes, time, imagePathStr);
            Event createdEvent = eventModel.createEvent(newEvent);

            if (createdEvent != null) {
                System.out.println("Event Added: " + createdEvent.getEventName());
                closeStage(actionEvent);
                eventManagerController.refreshEventTable();
            } else {
                System.out.println("Failed to add the event for an unknown reason.");
                showAlert("Event Error", "Event could not be added.");
            }
        } catch (DateTimeParseException e) {
            showAlert("Input Error", "Please enter a valid time in HH:mm format.");
            System.err.println("Time parsing error: " + e.getMessage());
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

    private LocalTime parseTime(String timeStr) throws DateTimeParseException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeStr, timeFormatter);
    }

    private EventManagerController eventManagerController;

    public void setEventManagerController(EventManagerController controller) {
        this.eventManagerController = controller;
    }

}