package XML.Gui.Controllers;

import XML.Gui.Models.UserModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private Button logInButton;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField passwordField;

    private UserModel model = new UserModel();


    //FXML Related Methods:
    @FXML
    public void logIn(ActionEvent actionEvent){
        boolean isAuthenticated = model.authenticateUser(userId.getText(), passwordField.getText());
        if (isAuthenticated) {
            switchSceneWithFade("/MainPage.fxml", "EASV Bar");
        } else {
            showAlert("Login Failed", "Wrong username or password");
        }

    }

    //Other Methods

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // Switch the Scene from log in to Main Page (For now Admin)
    private void switchSceneWithFade(String fxmlPath, String title) {
        Stage stage = (Stage) logInButton.getScene().getWindow();
        Parent currentRoot = logInButton.getScene().getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));
                /*
                 FileWriter myWriter = new FileWriter("Resources\\user.txt");
                 myWriter.write(userId.getText());
                 myWriter.close();
                */

                MainPageAdminController controller = loader.getController();

                // controller.setModel(model);

                // Prepare fade in transition for new scene
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();

                stage.setScene(scene);
                stage.setTitle(title);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page: " + title);
                alert.showAndWait();
            }
        });

        fadeOut.play();
    }


}
