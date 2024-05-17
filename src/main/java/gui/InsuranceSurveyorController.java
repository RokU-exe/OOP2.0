package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Claim;
import models.ClaimStatus;
import models.User;
import models.UserRole;
import utils.DBUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utils.DBUtil.getConnection;

public class InsuranceSurveyorController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
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

    public void proposeClaimToManager() throws SQLException {
        List<Claim> currentSurveyor = DBUtil.surveyorProposeToManager();

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
    }






    //PART OF FILTER CLAIM FUNCTION
    @FXML
    private Button filterClaimOption;
    @FXML
    private MenuButton statusSelection;
    @FXML
    private MenuButton policyHolderSelection;
    @FXML
    private MenuButton amountRangeSelection;
    @FXML
    Button findButton;

    private ClaimStatus getStatus(String status) {
        if (status.equals("NEW")) {
            return ClaimStatus.NEW;
        } else if (status.equals("PROCESSING")) {
            return ClaimStatus.PROCESSING;
        } else if (status.equals("APPROVED")) {
            return ClaimStatus.APPROVED;
        } else if (status.equals("REJECTED")) {
            return ClaimStatus.REJECTED;
        } else {
            // Handle the case when the role is not recognized
            // For example, you can throw an IllegalArgumentException
            throw new IllegalArgumentException("Unknown role: " + status);
        }
    }


    public void initialize() throws SQLException {
//        String st = null;
//        ClaimStatus statusOption = getStatus(st);
        List<String> options = DBUtil.selectPolicyHolderName();
        if (options != null) {
            for (String name : options) {
                MenuItem menuItem = new MenuItem(name);
                menuItem.setOnAction(event -> policyHolderSelection.setText(name));
                policyHolderSelection.getItems().add(menuItem);
            }
        }

//        if (statusOption != null) {
//            for (String statusOpt : statusOption) {
//                MenuItem menuItem = new MenuItem(statusOpt);
//                menuItem.setOnAction(event -> statusSelection.setText(statusOpt));
//                statusSelection.getItems().add(menuItem);
//            }
//        }
    }

    //MenuButton for selecting claim amount range

    public void createFilterClaimFXML() throws SQLException {
        // Create root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);

        // Create and configure "Filter Claim" label
        Label filterClaimLabel = new Label("Filter Claim");
        filterClaimLabel.setLayoutX(246);
        filterClaimLabel.setTextFill(Color.web("#ec1919"));
        filterClaimLabel.setFont(new Font("Calibri Bold", 24));

        // Create and configure "Status" label
        Label statusLabel = new Label("1. Status");
        statusLabel.setLayoutX(38);
        statusLabel.setLayoutY(73);
        statusLabel.setTextFill(Color.web("#b71212"));
        statusLabel.setFont(new Font(20));

        // Create and configure "Status" menu button
        statusSelection = new MenuButton("Select Status");
        statusSelection.setLayoutX(278);
        statusSelection.setLayoutY(72);
        MenuItem newStatus = new MenuItem("NEW");
        MenuItem processingStatus = new MenuItem("PROCESSING");
        MenuItem approvedStatus = new MenuItem("APPROVED");
        MenuItem rejectedStatus = new MenuItem("REJECTED");
        statusSelection.getItems().addAll(newStatus, processingStatus, approvedStatus, rejectedStatus);

        // Set action listeners for status menu items
        newStatus.setOnAction(event -> statusSelection.setText(newStatus.getText()));
        processingStatus.setOnAction(event -> statusSelection.setText(processingStatus.getText()));
        approvedStatus.setOnAction(event -> statusSelection.setText(approvedStatus.getText()));
        rejectedStatus.setOnAction(event -> statusSelection.setText(rejectedStatus.getText()));

        // Create and configure "Policy Holder" label
        Label policyHolderLabel = new Label("2. Policy Holder");
        policyHolderLabel.setLayoutX(38);
        policyHolderLabel.setLayoutY(159);
        policyHolderLabel.setTextFill(Color.web("#b71212"));
        policyHolderLabel.setFont(new Font(20));

        // Create and configure "Policy Holder" menu button
        policyHolderSelection = new MenuButton("Select Policy Holder");
        policyHolderSelection.setLayoutX(278);
        policyHolderSelection.setLayoutY(158);

        // Create and configure "Claim Amount Range" label
        Label amountRangeLabel = new Label("3. Claim Amount Range");
        amountRangeLabel.setLayoutX(38);
        amountRangeLabel.setLayoutY(242);
        amountRangeLabel.setTextFill(Color.web("#b71212"));
        amountRangeLabel.setFont(new Font(20));

        // Create and configure "Claim Amount Range" menu button
//        amountRangeSelection = new MenuButton("Select Amount Range");
//        amountRangeSelection.setLayoutX(277);
//        amountRangeSelection.setLayoutY(241);
//        MenuItem under1000 = new MenuItem("Under 1000");
//        MenuItem between1000And2000 = new MenuItem("1000 - 2000");
//        MenuItem above2000 = new MenuItem("Above 2000");
//        amountRangeSelection.getItems().addAll(under1000, between1000And2000, above2000);
//
//        // Set action listeners for amount range menu items
//        under1000.setOnAction(event -> amountRangeSelection.setText(under1000.getText()));
//        between1000And2000.setOnAction(event -> amountRangeSelection.setText(between1000And2000.getText()));
//        above2000.setOnAction(event -> amountRangeSelection.setText(above2000.getText()));

        // Create and configure "Find" button
        findButton = new Button("Find");
        findButton.setLayoutX(278);
        findButton.setLayoutY(312);
        findButton.setOnAction(event -> {
            try {
                findFilterClaim();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Add all components to the root pane
//        root.getChildren().addAll(
//                filterClaimLabel,
//                statusLabel,
//                statusSelection,
//                policyHolderLabel,
//                policyHolderSelection,
//                amountRangeLabel,
//                amountRangeSelection,
//                findButton
//        );

        root.getChildren().addAll(
                filterClaimLabel,
                statusLabel,
                statusSelection,
                policyHolderLabel,
                policyHolderSelection,
                amountRangeLabel,

                findButton
        );
        initialize();
        filterClaimOption.getScene().setRoot(root);
    }


    private String extractIdFromText(String text) {
        if (text == null || text.isEmpty()) {
            return null; // Handle empty text
        }
        // Assuming the ID is at the end, separated by a space or hyphen
        int indexOfSeparator = text.lastIndexOf(" ") + 1; // Adjust for different separators
        if (indexOfSeparator > 0) {
            return text.substring(indexOfSeparator);
        } else {
            return text; // If no separator found, return the entire text (might need adjustment)
        }
    }

    //Method to retrieve filter claim
    private void findFilterClaim() throws SQLException {
        String statusSelect = extractIdFromText(statusSelection.getText()); // Get selected card holder
//        ClaimStatus sta = getStatus(statusSelect);
        String policyHolderSelect = extractIdFromText(policyHolderSelection.getText());
//        String amountRangeSelect = extractIdFromText(amountRangeSelection.getText());

//        List<String> filteredClaims = DBUtil.surveyorGetFilterClaim(statusSelect, policyHolderSelect, amountRangeSelect);
        // Do something with the filteredClaims, such as displaying them in the UI or processing them further
        List<String> filterClaim = DBUtil.surveyorGetFilterClaim(statusSelect, policyHolderSelect/*, amountRangeSelect*/);
        if (filterClaim != null) {
            // Display the claim information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Claim Information");
            alert.setHeaderText("Claim Retrieved Successfully");
            alert.setContentText(filterClaim.toString());
            alert.showAndWait();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/InsuranceSurveyorGUI/ViewFilterClaim.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = findButton; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }
        }





}
