package XML.Gui.Controllers;

import XML.Be.Event;
import XML.Gui.Models.EventModel;
import XML.Gui.Models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class EventItemController {
    @FXML
    private ImageView eventImage;
    @FXML
    private Label titleLabel, dateLabel, cityLabel, priceLabel;

    private UserModel userModel;
    private EventModel eventModel;
    private Event event;


    public EventItemController() {
        try {
            userModel = new UserModel();
            eventModel = new EventModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    public void onEventItemClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPages/EventPage.fxml"));
            Parent root = loader.load();

            EventPageController eventPageController = loader.getController();
            eventPageController.setEventData(this.event);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the event details page.");
        }
    }



    public void setEventData(Event event) {
        this.event = event;

        titleLabel.setText(event.getEventName());
        dateLabel.setText(event.getDate().toString());
        cityLabel.setText(event.getCity());
        priceLabel.setText(String.format("%s DKK", event.getPrice().toPlainString()));

        // Image logic
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
