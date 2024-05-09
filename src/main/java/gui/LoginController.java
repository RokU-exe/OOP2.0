package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.User;
import models.UserRole;
import utils.DBUtil;

public class LoginController {
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

    private void openAdminDashboard(User user) {
        // Open the Admin Dashboard
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