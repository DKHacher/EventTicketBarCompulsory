package XML.Gui.Controllers;

import XML.Be.User;
import XML.Gui.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
    @FXML
    private TextField firstNameField, lastNameField, emailField, usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;

    private UserModel userModel;

    public SignUpController() throws Exception {
        userModel = new UserModel();
    }

    @FXML
    private void handleSignUp(ActionEvent actionEvent) {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            int userType = 2;

            User newUser = new User(0, username, firstName, lastName, email, password, userType); // ID is auto-generated

            User createdUser = userModel.createUser(newUser);

            if (createdUser != null) {
                //User was successfully created
                System.out.println("User successfully created!");
                closeStage(actionEvent);
            } else {
                //Handle the case where the user wasn't created.
                System.out.println("User creation failed for an unknown reason.");
            }
        } catch (Exception e) {
            //Username already exists
            System.err.println("Error during user registration: " + e.getMessage());
            showAlert("Username Exists", "Username already exists.");
            e.printStackTrace();
        }
    }

    private void closeStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
