package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Claim;
import models.User;
import utils.DBUtil;

import java.io.IOException;
import java.util.List;

public class InsuranceSurveyorController {
    void openSurveyorDashboard(Button loginButton) {
        // Open the Insurance Surveyor Dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/InsuranceSurveyorGUI/InsuranceSurveyor.fxml"));
            Parent insuranceSurveyorDashboardRoot = loader.load();
            Scene insuranceSurveyorDashboardScene = new Scene(insuranceSurveyorDashboardRoot);

            // Get any node from the current scene
            Node sourceNode = loginButton; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(insuranceSurveyorDashboardScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }


    //METHOD TO REVIEW CLAIM (WHICH STATUS = NEW), THEN MOVE TO ReviewClaimOption FXML
    @FXML
    private Button reviewClaimButton;
    public  Button getReviewClaimButton() {
        return reviewClaimButton;
    }
    public void reviewClaim(){
        List<Claim> currentSurveyor = DBUtil.surveyorReviewClaim();

        if (currentSurveyor != null) {
            // Display the claim information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Review Claim Information");
            alert.setHeaderText("Claim Retrieved Successfully");
            alert.setContentText(currentSurveyor.toString());
            alert.showAndWait();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/InsuranceSurveyorGUI/ReviewClaimOption.fxml"));
                Parent adminDashboardRoot = loader.load();

                Node sourceNode = reviewClaimButton;

                // Get the primary stage from the current scene
                Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

                primaryStage.setScene(new Scene(adminDashboardRoot));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where claim information could not be retrieved
            System.out.println("Error: Claim information not available.");
        }
    }

    @FXML
    private Button proposeClaimToManager;
    public  Button getProposeClaimToManager() {
        return proposeClaimToManager;
    }

    public void proposeClaimToManager(){
        List<Claim> currentSurveyor = DBUtil.surveyorProposeToManager();

        if (currentSurveyor != null) {
            // Display the claim information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Claim Information Valid");
            alert.setHeaderText("Claim Proposed Sucessfully");
            //alert.setContentText(currentSurveyor.toString());
            alert.showAndWait();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/InsuranceSurveyorGUI/InsuranceSurveyor.fxml"));
                Parent adminDashboardRoot = loader.load();

                Node sourceNode = proposeClaimToManager;

                // Get the primary stage from the current scene
                Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

                primaryStage.setScene(new Scene(adminDashboardRoot));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where claim information could not be retrieved
            System.out.println("Error: Claim information not available.");
        }
    }




}
