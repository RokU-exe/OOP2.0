package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import models.Claim;
import models.Customer;
import models.Surveyor;
import utils.DBUtil;

import java.util.List;

public class ManagerController {
    @FXML
    private Label contentLabel;

    @FXML
    private Button logoutButton, viewClaimsButton, viewCustomersButton, viewSurveyorsButton, approveClaimButton, rejectClaimButton;

    @FXML
    private VBox contentArea;

    @FXML
    private void handleLogout() {
        // Example logout logic, replace with actual implementation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout Successful");
        alert.setContentText("You have successfully logged out.");
        alert.showAndWait();
        System.out.println("Logging out...");
    }

    @FXML
    private void handleViewClaims() {
        // Retrieve claims data and display
        List<Claim> claims = DBUtil.getAllClaims();
        StringBuilder claimsInfo = new StringBuilder("Claims:\n\n");
        for (Claim claim : claims) {
            claimsInfo.append(String.format("ID: %d, Policy Holder: %s, Status: %s\n",
                    claim.getId(), claim.getPolicyHolderName(), claim.getStatus()));
        }
        contentLabel.setText(claimsInfo.toString());
    }

    @FXML
    private void handleViewCustomers() {
        // Retrieve customer data and display
        List<Customer> customers = DBUtil.getAllCustomers();
        StringBuilder customersInfo = new StringBuilder("Customers:\n\n");
        for (Customer customer : customers) {
            customersInfo.append(String.format("ID: %d, Name: %s, Role: %s\n",
                    customer.getId(), customer.getFullName(), customer.getRole()));
        }
        contentLabel.setText(customersInfo.toString());
    }

    @FXML
    private void handleViewSurveyors() {
        // Retrieve surveyor data and display
        List<Surveyor> surveyors = DBUtil.getAllSurveyors();
        StringBuilder surveyorsInfo = new StringBuilder("Surveyors:\n\n");
        for (Surveyor surveyor : surveyors) {
            surveyorsInfo.append(String.format("ID: %d, Name: %s\n",
                    surveyor.getId(), surveyor.getName()));
        }
        contentLabel.setText(surveyorsInfo.toString());
    }

    @FXML
    private void handleApproveClaim() {
        Claim selectedClaim = getSelectedClaim();
        if (selectedClaim != null) {
            DBUtil.approveClaim(selectedClaim.getId());
            refreshClaims();
        } else {
            showAlert(Alert.AlertType.ERROR, "No Claim Selected", "Please select a claim to approve.");
        }
    }

    private Claim getSelectedClaim() {
        return null;
    }

    private void refreshClaims() {
        handleViewClaims();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}