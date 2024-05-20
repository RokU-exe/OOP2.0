package gui;

//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.MenuItem;
//import javafx.stage.Stage;
//import models.*;
//import utils.DBUtil;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import controllers.SystemAdmin;
import models.*;
import utils.DBUtil;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static models.UserRole.*;
import static utils.DBUtil.getAllUsers;
import static utils.DBUtil.getUserByIDandRole;

public class PolicyOwnerController implements Initializable {


    private PolicyOwner owner;

    @FXML
    private TextField email;
    @FXML
    private TextField fullname;
    @FXML
    private TextField password;
    @FXML
    private TextField userID;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }



    public PolicyOwnerController(PolicyOwner owner) {
        this.owner = owner;
    };

    public PolicyOwnerController() {
        //default constructor
    };

    @FXML
    private MenuButton beneficiary_role;

    private UserRole getRole(String role) {
        if (role.equals("Policy Owner")) {
            return POLICY_OWNER;
        } else if (role.equals("Policy Holder")) {
            return POLICY_HOLDER;
        } else if (role.equals("Dependent")) {
            return DEPENDENT;
        } else if (role.equals("Insurance Surveyor")) {
            return INSURANCE_SURVEYOR;
        } else if (role.equals("Insurance Manager")) {
            return INSURANCE_MANAGER;
        } else {
            // Handle the case when the role is not recognized
            // For example, you can throw an IllegalArgumentException
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    //1.start with opening the Owner Dashboard
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

    //2. enter the beneficiary menu
    @FXML
    private Button beneficiary_menu_button;
    //Adding beneficiaries and their roles
    @FXML
    public void beneficiary_Menu(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/Beneficiary_Menu.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = beneficiary_menu_button; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

    // enter the Interface of adding user
    @FXML
    private Button add_Bene_Button;
    public void add_beneficiary_window(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/Add_Beneficiary.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = add_Bene_Button; // Use any node from the current scene

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
    private void selectRole(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String roleName = menuItem.getText();

        // Set the selected role as the text of the MenuButton
        beneficiary_role.setText(roleName);
    }

    @FXML
    private Button beneficiary_Adding_Button;

    //add the user, go back to dashboard
    public void add_Bene() {
        String roleName = beneficiary_role.getText();
        System.out.println(roleName);
        // Check if a role is selected
        if (roleName.equals("Select Beneficiary's Role")) {
            System.out.println("Please select a valid role.");
            return;
        }

        UserRole userRole = getRole(roleName);
        String id = DBUtil.getLargestIdByUserRole(userRole);
        if (id == null) {
            // Handle case when no matching records are found
            System.out.println("No matching records found for role: " + roleName);
            return;
        }
        User user = new User(id, fullname.getText(), email.getText(), password.getText(), userRole);

        // Add a new user to the database
        DBUtil.addUser(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Add " + roleName);
        alert.setHeaderText(roleName + " Added Successfully!");
        alert.setContentText(user.toString());
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerDashBoard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = beneficiary_Adding_Button; // Use any node from the current scene

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
    private Button remove_Bene_Button;
    public void remove_beneficiary_window()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/Remove_Beneficiary.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = remove_Bene_Button; // Use any node from the current scene

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
    private Button remove_Bene;
    @FXML
    public void remove_Bene(String beneficiaryID, UserRole selectedRole) {
        // Check if beneficiary ID and role are provided
        if (beneficiaryID == null || beneficiaryID.isEmpty() || "Select Role".equals(selectedRole)) {
            System.out.println("Please provide a valid beneficiary ID and select a role.");
            return;
        }

        // Confirm removal operation with the user (optional)
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Removal");
        confirmationAlert.setHeaderText("Are you sure you want to remove the beneficiary?");
        confirmationAlert.setContentText("ID: " + beneficiaryID + "\nRole: " + selectedRole);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform the removal operation
            // You can replace this print statement with your actual removal logic
            System.out.println("Removing beneficiary with ID: " + beneficiaryID + " and Role: " + selectedRole);

            // Once the removal is successful, you may want to update the UI or take further actions
            // For example, you can clear the input fields or refresh the displayed data
            // fullname.clear(); // Assuming fullname is the TextField for beneficiary ID
            // beneficiary_role.setText("Select Role"); // Reset the role selection
        } else {
            System.out.println("Removal operation canceled.");
        }
    }

//    public void remove_Bene() {
//
//        String roleName = beneficiary_role.getText();
//        System.out.println(roleName);
//
//        UserRole userRole = getRole(roleName);
//        String id = "";
//        getUserByIDandRole(id,userRole);
//
//        User user1 = new User(id,roleName);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//
//        // Add a new user to the database
//        DBUtil.removeBeneficiary(user1);
//        alert.setTitle("Remove " + roleName);
//        alert.setHeaderText(roleName + " Removed Successfully!");
//        alert.setContentText(user1.toString());
//        alert.showAndWait();
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerDashBoard.fxml"));
//            Parent adminDashboardRoot = loader.load();
//
//            Node sourceNode = remove_Bene; // Use any node from the current scene
//
//            // Get the primary stage from the source node's scene
//            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();
//
//            primaryStage.setScene(new Scene(adminDashboardRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the admin dashboard FXML
//        }
//    }

//    public void remove_Bene() {
//        String roleName = beneficiary_role.getText();
//        System.out.println(roleName);
//        // Check if a role is selected
//        if (roleName.equals("Select Beneficiary's Role")) {
//            System.out.println("Please select a valid role.");
//            return;
//        }
//
//        UserRole userRole = getRole(roleName);
//        String id = DBUtil.getLargestIdByUserRole(userRole);
//        if (id == null) {
//            // Handle case when no matching records are found
//            System.out.println("No matching records found for role: " + roleName);
//            return;
//        }
//        User user1 = new User(id,userRole);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//
//        // Add a new user to the database
//        DBUtil.removeBeneficiary(user1);
//        alert.setTitle("Remove " + roleName);
//        alert.setHeaderText(roleName + " Removed Successfully!");
//        alert.setContentText(user1.toString());
//        alert.showAndWait();
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerDashBoard.fxml"));
//            Parent adminDashboardRoot = loader.load();
//
//            Node sourceNode = remove_Bene; // Use any node from the current scene
//
//            // Get the primary stage from the source node's scene
//            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();
//
//            primaryStage.setScene(new Scene(adminDashboardRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the admin dashboard FXML
//        }
//    }





    @FXML
    private Button back;
    public void backtoDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SystemAdminGUI/systemAdminDashboard.fxml"));
            Parent adminDashboardRoot = loader.load();

            Node sourceNode = back; // Use any node from the current scene

            // Get the primary stage from the source node's scene
            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();

            primaryStage.setScene(new Scene(adminDashboardRoot));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any errors loading the admin dashboard FXML
        }
    }

//    @FXML // lam lai !!!!!!!!!
//    public void viewClaim() {
//        try {
//            // Retrieve all claims
//            List<Claim> claims = DBUtil.getAllClaims();
//
//            // Open the View_Claim view and set the claims data
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/View_Claim.fxml"));
//            Parent viewClaimRoot = loader.load();
//
//            ClaimsController claimsController = loader.getController();
//            claimsController.setClaims(claims);
//
//            // Get the stage from the viewClaimRoot
//            Stage primaryStage = new Stage();
//            primaryStage.setScene(new Scene(viewClaimRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the view
//        }
//    }

//    @FXML
//    public void viewClaim() throws SQLException {
//        // Retrieve all claims
//        List<Claim> claims = DBUtil.getAllClaims();
//
//        // Open the View_Claim view and set the claims data
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/View_Claim.fxml"));
//            Parent viewClaimRoot = loader.load();
//
//            ClaimsController claimsController = loader.getController();
//            claimsController.setClaims(claims);
//
//            Stage primaryStage = (Stage) viewClaimButton.getScene().getWindow();
//            primaryStage.setScene(new Scene(viewClaimRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the view
//        }
//    }


//    @FXML
//    private Button viewClaimButton;
//    public void viewClaim() throws SQLException {
//        String policyHolder_id = "";
//
//        // Call the getAllClaimsForPolicyOwner method from DBUtil to retrieve all claims for the policy owner
//        List<Claim> claims = getClaimsForPolicyOwner(policyHolder_id);
//
//        // Perform further actions with the retrieved claims, such as displaying them in the UI
//
//
//    }
//    public void openViewClaim() {
//
//        // Open the Policy Owner Dashboard
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PolicyOwnerGUI/View_Claim.fxml"));
//            Parent viewClaimRoot = loader.load();
//
////            ClaimsController claimsController = loader.getController();
////            claimsController.setClaims(claims);
//
//            Node sourceNode = viewClaimButton; // Use any node from the current scene
//
//            // Get the primary stage from the source node's scene
//            Stage primaryStage = (Stage) sourceNode.getScene().getWindow();
//
//            primaryStage.setScene(new Scene(viewClaimRoot));
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle any errors loading the admin dashboard FXML
//        }
//    }

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

