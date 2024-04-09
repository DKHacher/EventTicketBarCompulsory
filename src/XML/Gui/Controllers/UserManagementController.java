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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagementController {
    @FXML
    private TableView<User> tblUsers;
    @FXML
    private TableColumn<User, String> tblUserUsername, tblUserPassword, tblUserEmail, tblUserFirstName, tblUserLastName;
    @FXML
    private TableView<User> tblCoordinator;
    @FXML
    private TableColumn<User, String> tblCoordinatorUsername, tblCoordinatorPassword, tblCoordinatorEmail, tblCoordinatorFirstName, tblCoordinatorLastName;
    @FXML
    private TextField searchUserField;
    @FXML
    private TextField searchCoordinatorField;


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
            // Clear the current user list and table items before reloading
            userList.clear();
            tblUsers.getItems().clear();
            tblCoordinator.getItems().clear();

            userList.addAll(userModel.getAllUsers());

            for (User user : userList) {
                if (user.getUserType() == 2) {
                    tblUsers.getItems().add(user);
                } else if (user.getUserType() == 1) {
                    tblCoordinator.getItems().add(user);
                }
            }

            // Set cell value factories for columns
            tblUserUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tblUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            tblUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tblUserFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tblUserLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tblCoordinatorUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            tblCoordinatorPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            tblCoordinatorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tblCoordinatorFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tblCoordinatorLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Initialization Error", "Error Initializing Page.");
        }
    }


    // FXML Methods (Navigation)
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
        switchScene("/Tickets.fxml", "EASV Bar");
    }

    @FXML
    private void userManagement(ActionEvent event) {
        switchScene("/UserManagement.fxml", "EASV Bar");
    }

    @FXML
    private void eventManagement(ActionEvent event) {
        switchScene("/EventManager.fxml", "EASV Bar");
    }


    // Page Specific FXML methods
    @FXML
    private void handleDeleteUser(ActionEvent event) {
        User selectedUser = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userModel.deleteUser(selectedUser);
                refreshTables();
            } catch (Exception e) {
                showAlert("Error", "Failed to delete user.");
                e.printStackTrace();
            }
        } else {
            showAlertInfo("Selection Error", "Please select a User to delete.");
        }
    }

    @FXML
    private void handleAssign(ActionEvent event) {
        User selectedUser = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userModel.assignCoordinator(selectedUser.getId());
                refreshTables();
            } catch (Exception e) {
                showAlert("Error", "Failed to assign coordinator role.");
                e.printStackTrace();
            }
        } else {
            showAlertInfo("Selection Error", "Please select a User to assign.");
        }
    }



    @FXML
    private void handleUnassign(ActionEvent event) {
        User selectedCoordinator = tblCoordinator.getSelectionModel().getSelectedItem();
        if (selectedCoordinator != null) {
            try {
                userModel.unassignCoordinator(selectedCoordinator.getId()); // Update database
                refreshTables(); // Reload the tables
            } catch (Exception e) {
                showAlert("Error", "Failed to unassign coordinator role.");
                e.printStackTrace();
            }
        } else {
            showAlertInfo("Selection Error", "Please select a Coordinator to unassign.");
        }
    }

    @FXML
    private void handleSearchUser(KeyEvent event) {
        filterUsers(searchUserField.getText());
    }

    @FXML
    private void handleSearchCoordinator(KeyEvent event) {
        filterCoordinators(searchCoordinatorField.getText());
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
            showAlert("Loading Error", "Could not load the page.");
        }
    }

    private void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshTables() {
        tblUsers.getItems().clear();
        tblCoordinator.getItems().clear();
        initialize();
    }

    private void filterUsers(String searchText) {
        List<User> filteredUsers = userList.stream()
                .filter(user -> user.getUserType() == 2 && user.toString().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        tblUsers.getItems().setAll(filteredUsers);
    }

    private void filterCoordinators(String searchText) {
        List<User> filteredCoordinators = userList.stream()
                .filter(user -> user.getUserType() == 1 && user.toString().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());

        tblCoordinator.getItems().setAll(filteredCoordinators);
    }

}
