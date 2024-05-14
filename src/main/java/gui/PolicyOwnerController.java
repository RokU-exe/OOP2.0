package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.*;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PolicyOwnerController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private PolicyOwner owner;

    public PolicyOwnerController(PolicyOwner owner) {
    };

    public PolicyOwnerController(PolicyOwner owner, List<PolicyHolder> policyHolders) {}

    public PolicyOwnerController() {
        //default constructor
    };




    //Initialization of buttons in PolicyOwner Dashboard
    @FXML
    private Button policyHolderChooseButton;

    @FXML
    private Button dependentChooseButton;

    @FXML
    private Button backtoDashoardButton;

    @FXML
    private Button updateClaimButtom;
    public void updateClaim()
    {

    }
     public void openPolicyOwnerDashboard(Button loginButton) {
        // Open the Policy Owner Dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerDashBoard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = loginButton; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    @FXML
    private Button fileClaimButton;
    public void fileClaim() {
        // Call the getAllClaimsForPolicyOwner method from DBUtil to retrieve all claims for the policy owner
        List<Claim> claims = DBUtil.getAllClaimsForPolicyOwner();

        // Perform further actions with the retrieved claims, such as displaying them in the UI
        for (Claim claim : claims) {
            System.out.println("Claim ID: " + claim.getId());
            // Display other relevant information about the claim
        }
    }

    @FXML
    private Button viewClaimButton;
    public void viewClaim() {
        // Call the getAllClaimsForPolicyOwner method from DBUtil to retrieve all claims for the policy owner
        List<Claim> claims = DBUtil.getAllClaimsForPolicyOwner();

        // Perform further actions with the retrieved claims, such as displaying them in the UI
        for (Claim claim : claims) {
            System.out.println("Claim ID: " + claim.getId());
            // Display other relevant information about the claim
        }
    }
    @FXML
    private Button deleteClaimButton;
    public void deleteClaim()
    {

    }




    //Methods for buttons in next fxml files

//    // Method to handle adding a new policy holder
//    public void addPolicyHolder() {
//        // Implement logic to add a new policy holder
//    }
//
//    // Method to handle updating an existing policy holder
//    public void updatePolicyHolder() {
//        // Implement logic to update an existing policy holder
//    }
//
//    // Method to handle retrieving policy holders
//    public void retrievePolicyHolders() {
//        // Implement logic to retrieve policy holders
//    }
//
//    // Method to handle deleting a policy holder
//    public void deletePolicyHolder() {
//        // Implement logic to delete a policy holder
//    }
//
//    // Method to handle adding a new dependent
//    public void addDependent() {
//        // Implement logic to add a new dependent
//    }
//
//    // Method to handle updating an existing dependent
//    public void updateDependent() {
//        // Implement logic to update an existing dependent
//    }

    // Method to handle retrieving dependents
    // Method to handle "Get Dependents" button
//    @FXML
//    private void getDependents( {
//        // Implement logic to retrieve dependents
//        // For example:
//        User currentUser;
//        List<Dependent> dependents = PolicyHolder.getDependents(currentUser);
//
//        // Once dependents are retrieved, you can display them or perform further actions
//        for (Dependent dependent : dependents) {
//            System.out.println("Dependent Name: " + dependent.getName());
//            System.out.println("Relationship: " + dependent.getRelationship());
//            // Display other relevant information about the dependent
//        }
//    }

    // Method to handle deleting a dependent
//    public void deleteDependent() {
//        // Implement logic to delete a dependent
//    }
}
