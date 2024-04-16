package XML.Gui.Controllers;

import XML.Be.PromoTicket;
import XML.Gui.Models.TicketModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class TicketAddController {
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descField;
    private TicketModel ticketModel;
    private TicketsController ticketsController;


    public TicketAddController() {
        try {
            ticketModel = new TicketModel();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unknown Error.");
        }
    }

    @FXML
    private void addEvent(ActionEvent actionEvent) {
        try {
            String ticketType = titleField.getText().trim();
            String ticketDescription = descField.getText().trim();

            PromoTicket newPromoTicket = new PromoTicket(0, ticketType, ticketDescription);
            PromoTicket createdPromoTicket = ticketModel.createPromoTicket(newPromoTicket);

            if (createdPromoTicket != null) {
                System.out.println("Promo Added: " + createdPromoTicket.getTicketType());
                closeStage(actionEvent);
                ticketsController.refreshPromoTicketTable();
            } else {
                System.out.println("Failed to add the promo for an unknown reason.");
                showAlert("Promo Error", "Promo could not be added.");
            }
        } catch (Exception e) {
            showAlert("Promo Error", "Please check your input fields.");
            System.err.println("Error during promo addition: " + e.getMessage());
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

    public void setEventManagerController(TicketsController controller) {
        this.ticketsController = controller;
    }

}