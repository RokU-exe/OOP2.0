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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static utils.DBUtil.getClaimsForPolicyOwner;

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
    private Button viewClaimButton;
    public void viewClaim() throws SQLException {
        // Call the getAllClaimsForPolicyOwner method from DBUtil to retrieve all claims for the policy owner
        List<Claim> claims = getClaimsForPolicyOwner();

        // Perform further actions with the retrieved claims, such as displaying them in the UI


    }
    public void openViewClaim() {

        // Open the Policy Owner Dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/View_Claim.fxml"));
            Parent viewClaimRoot = loader.load();

//            ClaimsController claimsController = loader.getController();
//            claimsController.setClaims(claims);

            Node sourceNode = viewClaimButton; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(viewClaimRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    @FXML
    private Button deleteClaimButton;
    public void deleteClaim()
    {

    }





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


//    @FXML
//    private Button fileClaimButton;
//    public List<Claim> fileClaim() {
//        // Call the getAllClaimsForPolicyOwner method from DBUtil to retrieve all claims for the policy owner
//        List<Claim> claims = DBUtil.getAllClaims();
//
//        // Perform further actions with the retrieved claims, such as displaying them in the UI
//        for (Claim claim : claims) {
//            System.out.println("Claim ID: " + claim.getId());
//            // Display other relevant information about the claim
//        }
//        return claims;
//    }


}
