package XML.Gui.Controllers;

import XML.Gui.Models.UserModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button logInButton;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ProgressIndicator progressIndicator;

    private UserModel model;

    {
        try {
            model = new UserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        userId.setOnKeyPressed(this::handleEnterPressed);
        passwordField.setOnKeyPressed(this::handleEnterPressed);
        progressIndicator.setVisible(false);
    }

    private void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logIn(new ActionEvent());
        }
    }

    //FXML Related Methods:
    @FXML
    public void logIn(ActionEvent actionEvent) {
        boolean isAuthenticated = false;
        try {
            isAuthenticated = model.authenticateUser(userId.getText(), passwordField.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (isAuthenticated) {
            progressIndicator.setVisible(true);
            switchScene("/MainPages/Dashboard.fxml", "EASV Bar");
        } else {
            showAlert("Login Failed", "Wrong username or password");
        }
    }

    @FXML
    public void handleSignUp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pop-Ups/SignUp.fxml"));
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


    private void switchScene(String fxmlPath, String title) {
        // Progress indicator is visible
        progressIndicator.setVisible(true);

        // Task to load and switch scenes asynchronously
        Task<Void> loadSceneTask = new Task<Void>() {
            @Override
            protected Void call() throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Platform.runLater(() -> {
                    Scene scene = new Scene(root);
                    scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

                    DashboardController controller = loader.getController();

                    Stage stage = (Stage) logInButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle(title);
                    stage.centerOnScreen();

                    // Hide the progress indicator after scene has been set
                    progressIndicator.setVisible(false);
                });

                return null;
            }
        };
        loadSceneTask.setOnFailed(e -> {
            Throwable th = e.getSource().getException();
            th.printStackTrace();
            showAlert("Error", "Could not load the page: " + title);
            progressIndicator.setVisible(false);
        });
        // Start the task on a new thread
        new Thread(loadSceneTask).start();
    }



}
