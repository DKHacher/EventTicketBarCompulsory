package XML.Gui.Controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;

public class MainPageAdminController {
    @FXML
    private Pane accountPane;
    @FXML
    private Button accountButton, manageUsersBtn, eventBtn, dashboardBtn, ticketsBtn, logOutBtn;





    // Switch Scenes
    @FXML
    private void handleAccountButtonAction(ActionEvent actionEvent) {
        accountPane.setVisible(!accountPane.isVisible());
    }

    @FXML
    private void logOut(ActionEvent event) {
        switchSceneWithFade();
    }

    private void switchSceneWithFade() {
        Stage stage = (Stage) accountPane.getScene().getWindow();
        Parent currentRoot = stage.getScene().getRoot();

        // Prepare fade out transition for current scene
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Set the action to perform when fade out is completed
        fadeOut.setOnFinished(event -> {
            try {
                // Load the new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

                // Prepare fade in transition for new scene
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();

                stage.setScene(scene);
                // stage.setTitle(title);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page.");
                alert.showAndWait();
            }
        });

        // Start the fade out transition
        fadeOut.play();
    }
}
