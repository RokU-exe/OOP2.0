package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Claim;
import models.Customer;
import models.Surveyor;
import utils.DBUtil;

import java.io.IOException;
import java.util.List;

public class ManagerController {

    @FXML
    private Label contentLabel;

    @FXML
    private Button logoutButton, viewClaimsButton, viewCustomersButton, viewSurveyorsButton, approveClaimButton, rejectClaimButton, navigateButton;

    @FXML
    private VBox contentArea;

    @FXML
    private TableView<Claim> claimsTable;

    @FXML
    private TableColumn<Claim, Integer> claimIdColumn;

    @FXML
    private TableColumn<Claim, String> policyHolderColumn;

    @FXML
    private TableColumn<Claim, String> statusColumn;

    private ObservableList<Claim> claimsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        claimIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        policyHolderColumn.setCellValueFactory(new PropertyValueFactory<>("policyHolderName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        claimsTable.setItems(claimsData);
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout Successful");
        alert.setContentText("You have successfully logged out.");
        alert.showAndWait();
        System.out.println("Logging out...");
    }

    @FXML
    private void handleViewClaims() {
        List<Claim> claims = DBUtil.getAllClaims();
        claimsData.setAll(claims);
        StringBuilder claimsInfo = new StringBuilder("Claims:\n\n");
        for (Claim claim : claims) {
            claimsInfo.append(String.format("ID: %d, Policy Holder: %s, Status: %s\n",
                    claim.getId(), claim.getPolicyHolderName(), claim.getStatus()));
        }
        contentLabel.setText(claimsInfo.toString());
    }

    @FXML
    private void handleViewCustomers() {
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

    @FXML
    private void handleRejectClaim() {
        Claim selectedClaim = getSelectedClaim();
        if (selectedClaim != null) {
            DBUtil.rejectClaim(selectedClaim.getId());
            refreshClaims();
        } else {
            showAlert(Alert.AlertType.ERROR, "No Claim Selected", "Please select a claim to reject.");
        }
    }

    @FXML
    private void handleExamineClaims() throws IOException {
        navigateToPage("/gui/ManagerGUI/ExamClaims.fxml");
    }

    @FXML
    private void handleRetrieveUser() throws IOException {
        navigateToPage("/gui/ManagerGUI/RetrieveUser.fxml");
    }

    private void navigateToPage(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) navigateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private Claim getSelectedClaim() {
        return claimsTable.getSelectionModel().getSelectedItem();
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