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
import javafx.stage.Modality;
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

    private UserModel model;

    {
        try {
            model = new UserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //FXML Related Methods:
    @FXML
    public void logIn(ActionEvent actionEvent){
        boolean isAuthenticated = false;
        try {
            isAuthenticated = model.authenticateUser(userId.getText(), passwordField.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (isAuthenticated) {
            switchScene("/MainPageAdmin.fxml", "EASV Bar");
        } else {
            showAlert("Login Failed", "Wrong username or password");
        }

    }

    @FXML
    public void handleSignUp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();

            Stage signUpStage = new Stage();
            signUpStage.initModality(Modality.APPLICATION_MODAL); //Prevent interaction with the window behind
            signUpStage.setTitle("Sign Up");
            signUpStage.setScene(new Scene(root));

            signUpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Sign Up page");
        }
    }

    //Other Methods

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // Switch the Scene from log in to Main Page (For now Admin)
    private void switchScene(String fxmlPath, String title) {
        Stage stage = (Stage) logInButton.getScene().getWindow();

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

            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page: " + title);
            alert.showAndWait();
        }
    }



}
