package XML.Gui.Controllers;

import XML.Be.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EventItemController {
    @FXML
    private ImageView eventImage;
    @FXML
    private Label titleLabel, dateLabel, cityLabel, priceLabel;

    public void setEventData(Event event) {
        titleLabel.setText(event.getEventName());
        dateLabel.setText(event.getDate().toString());
        cityLabel.setText(event.getCity());
        priceLabel.setText(String.format("%s DKK", event.getPrice().toPlainString()));

        /*
        // For when there is images implemented:
        Image image = new Image(event.getImageUrl(), true);
        eventImageView.setImage(image);
        */
    }
}
