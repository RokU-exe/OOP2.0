package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import models.Claim;
import models.PolicyHolder;
import java.util.List;
import java.util.ResourceBundle;

public class PolicyHolderDashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<Claim> claimListView;
    @FXML
    private Button viewClaimDetailsButton;
    @FXML
    private Button fileNewClaimButton;
    @FXML
    private Button updateClaimButton;
    @FXML
    private Button accountSettingsButton;

    private PolicyHolder policyHolder;

    public <URL> void initialize(URL url, ResourceBundle resources) {
        // This method is called after FXML loading is complete
        if (policyHolder != null) {
            welcomeLabel.setText("Welcome, " + policyHolder.getFullName() + "!");
            loadClaimData(); // Load and display claims in the ListView
        }
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
        if (claimListView != null) { // Check if initialized
            loadClaimData();
        }
    }

    private void loadClaimData() {
        List<Claim> claims = policyHolder.getClaims(); // Fetch the claims
        claimListView.getItems().addAll(claims); // Populate the ListView
    }

    @FXML
    private void handleViewClaimDetails() {
        Claim selectedClaim = claimListView.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            // Open a new window/dialog to display the claim details
        }
    }

    @FXML
    private void handleFileNewClaim() {
        // Open a new window/dialog for filing a new claim
    }

    @FXML
    private void handleUpdateClaim() {
        Claim selectedClaim = claimListView.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            // Open a new window/dialog for updating the selected claim
        }
    }

    @FXML
    private void handleAccountSettings() {
        // Open a new window/dialog for account settings
    }
}
