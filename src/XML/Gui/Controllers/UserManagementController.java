package XML.Gui.Controllers;

import XML.Be.User;
import XML.Gui.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserManagementController {
    @FXML
    private TableView<User> tblUsers;
    @FXML
    private TableColumn<User, String> tblUserUsername;
    @FXML
    private TableColumn<User, String> tblUserPassword;
    @FXML
    private TableView<User> tblCoordinator;
    @FXML
    private TableColumn<User, String> tblCoordinatorUsername;
    @FXML
    private TableColumn<User, String> tblCoordinatorPassword;

    private ArrayList<User> userList = new ArrayList<>();
    private UserModel userModel;

    @FXML
    private Pane accountPane;

    public UserManagementController() {
        try {
            userModel = new UserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        try {
            userList.addAll(userModel.getAllUsers());

            for (User user : userList) {
                if (user.getUserType() == 1) {
                    tblUsers.getItems().add(user);
                } else if (user.getUserType() == 2) {
                    tblCoordinator.getItems().add(user);
                }
            }

            // Set cell value factories for username and password columns
            tblUserUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tblUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            tblCoordinatorUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tblCoordinatorPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // FXML Methods
    @FXML
    private void handleAccountButtonAction(ActionEvent actionEvent) {
        accountPane.setVisible(!accountPane.isVisible());
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        switchScene("/MainPageAdmin.fxml", "EASV Bar");
    }

    @FXML
    private void logOut(ActionEvent event) {
        switchScene("/LoginPage.fxml", "EASV Bar");
    }

    @FXML
    private void goToTickets(ActionEvent event) {
        // switchScene("/ticketsManagement.fxml", "EASV Bar");
    }

    @FXML
    private void handleDeleteCoordinator(ActionEvent event){

    }

    @FXML
    private void handleDeleteUser(ActionEvent event){

    }

    @FXML
    private void handleAssign(ActionEvent event){

    }

    @FXML
    private void handleUnassign(ActionEvent event){

    }

    // Other Methods


    private void switchScene(String fxmlPath, String title) {
        Stage stage = (Stage) accountPane.getScene().getWindow();

        try {
            // Load the new scene from the specified FXML path
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

            stage.setScene(scene);
            stage.setTitle(title); // Set the stage title
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page.");
            alert.showAndWait();
        }
    }
}
