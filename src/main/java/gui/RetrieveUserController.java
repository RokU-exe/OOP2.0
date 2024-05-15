package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Customer;
import utils.DBUtil;

public class RetrieveUserController {

    @FXML
    private TextField userIdField;

    @FXML
    private TextArea userInfoArea;

    @FXML
    private Button backButton;

    @FXML
    private void handleRetrieveUser() {
        String userId = userIdField.getText();
        if (userId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a user ID.");
            return;
        }

        try {
            Customer customer = DBUtil.getCustomerById(Integer.parseInt(userId));
            if (customer != null) {
                userInfoArea.setText("ID: " + customer.getId() + "\nName: " + customer.getFullName() + "\nRole: " + customer.getRole());
            } else {
                userInfoArea.setText("User not found.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "User ID must be a number.");
        }
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();  // or navigate to the previous scene
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
