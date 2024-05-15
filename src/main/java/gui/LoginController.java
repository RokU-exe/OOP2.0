package gui;

import gui.coolEffects.TextAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.SystemAdmin;
import models.User;
import models.UserRole;
import utils.DBUtil;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    public Button getLoginButton() {
        return loginButton;
    }

    private User getUser() {
        User user;
        String email = emailField.getText();
        String password = passwordField.getText();
        user = DBUtil.getUserByEmailAndPassword(email, password);
        return user;
    }

    public void validShow() {
        // Display a popup dialog for system admin
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Login");
        alert.setHeaderText("Login Successful");
        alert.setContentText("You have successfully logged in as a " + getUser().getRole());
        alert.showAndWait();
    }

    @FXML
    private void handleLogin() throws IOException {
        User user = getUser();
        if (user != null) {
            switch (user.getRole()) {
                case UserRole.POLICY_HOLDER -> {
                    validShow();
                    openPolicyHolderDashboard(user);
                }
                case UserRole.DEPENDENT -> {
                    validShow();
                    openDependentDashboard(user);
                }
                case UserRole.POLICY_OWNER -> {
                    validShow();
                    openPolicyOwnerDashboard(user);
                }
                case UserRole.INSURANCE_SURVEYOR -> {
                    validShow();
                    openSurveyorDashboard(user);
                }
                case UserRole.INSURANCE_MANAGER -> {
                    validShow();
                    openManagerDashboard(user);
                }
                case UserRole.SYSTEM_ADMIN -> {
                    validShow();
                    SystemAdmin systemAdmin = new SystemAdmin(user.getId(), user.getFullName(), user.getEmail(), user.getPassword(), UserRole.SYSTEM_ADMIN);
                    SystemAdminController controller = new SystemAdminController(systemAdmin);
                    controller.openAdminDashboard(getLoginButton());
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your email and password.");
            alert.showAndWait();
        }
    }
//    @FXML
//    // Open the Admin Dashboard
//    public void openAdminDashboard(Button login) {
//        try {
//            FXMLLoader loader = new FXMLLoader(SystemAdminController.class.getResource("/gui/systemAdminDashboard.fxml"));
//            Parent adminDashboardRoot = loader.load();
//            Scene adminDashboardScene = new Scene(adminDashboardRoot);
//            Node sourceNode = login;
//            // Get any node from the current scene
//
//            // Get the primary stage from the source node's scene
//            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();
//
//            primaryStage.setScene(adminDashboardScene);
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the admin dashboard FXML
//        }
//    }

    @FXML
    private void openPolicyHolderDashboard(User user) {
        // Open the Policy Holder Dashboard
    }

    private void openDependentDashboard(User user) {
        // Open the models.Dependent Dashboard
    }

    private void openPolicyOwnerDashboard(User user) {
        // Open the Policy Owner Dashboard
    }

    private void openSurveyorDashboard(User user) {
        // Open the Surveyor Dashboard
    }

    private void openManagerDashboard(User user) {
        // Open the Manager Dashboard
    }

}

// Full version after create full GUI, FXML File

/* public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = DBUtil.getUserByEmailAndPassword(email, password);
        if (user != null) {
            switch (user.getRole()) {
                case UserRole.POLICY_HOLDER -> openPolicyHolderDashboard(user);
                case UserRole.DEPENDENT -> openDependentDashboard(user);
                case UserRole.POLICY_OWNER -> openPolicyOwnerDashboard(user);
                case UserRole.INSURANCE_SURVEYOR -> openSurveyorDashboard(user);
                case UserRole.INSURANCE_MANAGER -> openManagerDashboard(user);
                case UserRole.SYSTEM_ADMIN -> openAdminDashboard(user);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your email and password.");
            alert.showAndWait();
        }
    }

    private void openPolicyHolderDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PolicyHolderDashboard.fxml"));
            Parent root = loader.load();
            PolicyHolderDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDependentDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DependentDashboard.fxml"));
            Parent root = loader.load();
            DependentDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPolicyOwnerDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PolicyOwnerDashboard.fxml"));
            Parent root = loader.load();
            PolicyOwnerDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openSurveyorDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SurveyorDashboard.fxml"));
            Parent root = loader.load();
            SurveyorDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openManagerDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerDashboard.fxml"));
            Parent root = loader.load();
            ManagerDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAdminDashboard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
            Parent root = loader.load();
            AdminDashboardController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/