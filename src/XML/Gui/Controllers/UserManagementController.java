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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserManagementController {
    @FXML
    private TableView tblUsers;
    @FXML
    private TableColumn tblUserUsername;
    @FXML
    private TableColumn tblUserPassword;
    @FXML
    private TableView tblCoordinator;
    @FXML
    private TableColumn tblCoordinatorUsername;
    @FXML
    private TableColumn tblCoordinatorPassword;




    private ArrayList<User> userList = new ArrayList<>();
    private UserModel userModel;
    {
        try {
            userModel = new UserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Pane accountPane;

    public UserManagementController() {
        tblUsers = new TableView<>();
        tblCoordinator = new TableView<>();
        try {
            userList.addAll(userModel.getAllUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (User user:userList) {
            if (user.getUserType() == 1){
                tblUsers.getItems().add(user);
                tblUserUsername.getColumns().add(user.getUsername());
                tblUserPassword.getColumns().add(user.getPassword());
            }
            else if (user.getUserType() ==2){
                tblCoordinator.getItems().add(user);
            }
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
